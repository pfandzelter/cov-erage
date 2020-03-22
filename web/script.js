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
  template: '<at-button>Weiter<i class="icon icon-chevron-right"></i></at-button>'
});

let backbutton = Vue.component("back-button", {
  template: '<at-button type="text"><i class="icon icon-chevron-left"></i>Zurück</at-button>'
});

let submitbutton = Vue.component("submit-button", {
  template: '<at-button type="primary">Absenden<i class="icon icon-chevrons-right"></i></at-button>'
});

let v = new Vue({
  el: "#app",
  data: () => ({
    step: 1,
    entry: {
      generalHealth: 5,
      coronaVirus: -1,
      numberOfContacts: 2,
      coughing: -1,
      temperature: -1,
      headache: -1,
      soreThroat: -1,
      runnyNose: -1,
      limbPain: -1,
      diarrhea: -1,
      loneliness: -1,
      insomnia: -1
    },
    user: {
      id: null,
      name: null,
      zip: "00000",
      birth_year: 1900,
      gender: -1
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
      this.step = 7;
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
    submit() {
      let droplet = {
        userId: this.id,
        postalCode: this.user.zip,
        yearOfBirth: this.user.birth_year,
        gender: this.user.gender,
        generalHealth: this.generalHealth,
        coronaVirus: this.coronaVirus,
        numberOfContacts: this.numberOfContacts,
        coughing: this.coughing,
        temperature: this.temperature,
        headache: this.headache,
        soreThroat: this.soreThroat,
        runnyNose: this.runnyNose,
        limbPain: this.limbPain,
        diarrhea: this.diarrhea,
        loneliness: this.loneliness,
        insomnia: this.insomnia
      };

      axios({
        method: "post",
        url: "https://aiulvempz3.execute-api.eu-central-1.amazonaws.com/production/droplet",
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