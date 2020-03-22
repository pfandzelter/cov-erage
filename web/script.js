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
    step: "signup",
    isActive: true,
    entrystep: 1,
    signupstep: 1,
    waittime: 0,
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
      name: "",
      zip: "00000",
      birth_year: 1900,
      gender: -1,
      lastentry: new Date(0).getTime()
    },
    response: null,
    submitfailed: false
  }),
  components: {
    backbutton: backbutton,
    fwdbutton: fwdbutton,
    submitbutton: submitbutton
  },
  computed: {
    invalidName: () => {
      return !(/^([A-Z][a-z,\-,\ ,\.]{0,40})$/).test(v.user.name);
    },
    invalidZIP: () => {
      if (v.user.zip == "00000") return true;
      return !(/^([0-9]{5})$/).test(v.user.zip);
    }
  },
  mounted() {
    if (localStorage.user) {
      this.user = JSON.parse(localStorage.user);

      this.waittime = (24 * 60) - Math.ceil(Math.abs(((new Date()).getTime() - this.user.lastentry)) / (60 * 1000));

      if (this.waittime <= 0) {
        this.step = 'entry';
      } else {
        this.step = 'wait';
      }

      //update waittime every 10 seconds
      setInterval(() => {
        this.waittime = (24 * 60) - Math.ceil(Math.abs(((new Date()).getTime() - this.user.lastentry)) / (60 * 1000));

        if (this.step == 'wait' && this.waittime <= 0) {
          this.entrystep = 0;
          this.step = 'entry';
        }
      }, 1000);
    } else {
      this.user.id = uuidv4();
    }
  },
  methods: {
    persistsignup() {
      localStorage.user = JSON.stringify(this.user);
      this.signupstep = 0;
      this.step = 'entry';
    },
    prevsignup() {
      this.signupstep--;
      console.log(this.signupstep);
    },
    async nextsignup() {
      await timeout(250);
      this.signupstep++;
      console.log(this.signupstep);
    },
    preventry() {
      this.entrystep--;
      console.log(this.entrystep);
    },
    async nextentry() {
      await timeout(250);
      this.nextentrystep();
    },
    nextentrystep() {
      this.entrystep++;
      console.log(this.entrystep);
    },
    submitentry() {
      let droplet = {
        userId: this.user.id,
        postalCode: this.user.zip,
        yearOfBirth: this.user.birth_year,
        gender: this.user.gender,
        generalHealth: this.entry.generalHealth,
        coronaVirus: this.entry.coronaVirus,
        numberOfContacts: this.entry.numberOfContacts,
        coughing: this.entry.coughing,
        temperature: this.entry.temperature,
        headache: this.entry.headache,
        soreThroat: this.entry.soreThroat,
        runnyNose: this.entry.runnyNose,
        limbPain: this.entry.limbPain,
        diarrhea: this.entry.diarrhea,
        loneliness: this.entry.loneliness,
        insomnia: this.entry.insomnia
      };

      this.user.lastentry = (new Date()).getTime();

      console.log(droplet)

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
            this.nextentry();
            localStorage.user = JSON.stringify(this.user);
          } else {
            this.submitfailed = true;
            this.response = response.body;
            this.nextentry();
          }
        },
        err => {
          console.log(err);
          this.submitfailed = true;
          this.response = err;
          this.nextentry();
        }
      );

      this.nextentry();
    }
  }
});