function uuidv4() {
  return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, c =>
    (
      c ^
      (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))
    ).toString(16)
  );
}

function timeout(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

let fwdbutton = Vue.component("forward-button", {
  template:
    '<at-button>Weiter<i class="icon icon-chevron-right"></i></at-button>'
});

let backbutton = Vue.component("back-button", {
  template:
    '<at-button type="text"><i class="icon icon-chevron-left"></i>Zurück</at-button>'
});

let submitbutton = Vue.component("submit-button", {
  template:
    '<at-button type="primary">Absenden<i class="icon icon-chevrons-right"></i></at-button>'
});

let v = new Vue({
  el: "#app",
  data: () => ({
    step: 1,
    symptomstep: 0,
    symptoms: [
      ["Husten 😷", "cough"],
      ["Schlafmangel 🥱", "insomnia"],
      ["Einsamkeit 😪", "loneliness "],
      ["Übelkeit 🤢", "sickness"],
      ["Fieber 🤒", "temperature"],
      ["Schnupfen 🤧", "sneezing"],
      ["Erschöpfung 😴", "exhaustion"]
    ],
    entry: {
      feeling: 0,
      symptoms: {}
    },
    user: {
      id: null,
      name: null,
      zip: null,
      birth_year: null,
      preex_cond: [],
      gender: null
    },
    response: null,
    submitfailed: false
  }),
  components: {
    backbutton: backbutton,
    fwdbutton: fwdbutton,
    submitbutton: submitbutton
  },
  mounted() {
    if (localStorage.user) {
      this.step = 8;
      this.user = JSON.parse(localStorage.user);
    } else {
      this.user.id = uuidv4();
    }
  },
  methods: {
    persist() {
      localStorage.user = JSON.stringify(this.user);
      this.next();
    },
    prev() {
      console.log(this.step);
      this.step--;
    },
    async nextstep() {
      await timeout(250);
      this.next();
    },
    next() {
      console.log(this.step);
      this.step++;
    },
    async nextsymptom() {
      await timeout(250);
      if (this.symptomstep < this.symptoms.length - 1) {
        this.symptomstep++;
      } else {
        this.nextstep();
      }
    },
    prevsymptom() {
      if (this.symptomstep > 0) {
        this.symptomstep--;
      } else {
        this.prev();
      }
    },
    submit() {
      let droplet = {
        postalCode: this.user.zip,
        yearOfBirth: this.user.birth_year,
        gender: this.user.gender,
        healthState: this.entry.feeling,
        symptoms: this.entry.symptoms
      };

      axios({
        method: "post",
        url:
          "https://aiulvempz3.execute-api.eu-central-1.amazonaws.com/production/droplet",
        data: droplet,
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          Accept: "application/json"
        }
      }).then(
        response => {
          console.log(response);
          if (response.status === 200) {
            this.submitfailed = false;
            this.next();
          } else {
            this.submitfailed = true;
            this.response = response.body;
            this.next();
          }
        },
        err => {
          console.log(err);
          this.submitfailed = true;
          this.response = err;
          this.next();
        }
      );

      this.next();
    }
  }
});
