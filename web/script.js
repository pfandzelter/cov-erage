new Vue({
  el: "#app",
  data: () => ({
    step: 1,
    symptomstep: 0,
    symptoms: ["Husten", "Schlafmangel", "Einsamkeit"],
    entry: {
      feeling: 0,
      symptoms: null
    },
    user: {
      name: null,
      zip: null,
      birth_year: null,
      preex_cond: [],
      gender: null
    }
  }),
  mounted() {
    if (localStorage.user) {
      this.step = 8;
      this.user = JSON.parse(localStorage.user);
    }
  },
  methods: {
    persist() {
      localStorage.user = JSON.stringify(this.user);
      this.next();
    },
    prev() {
      this.step--;
    },
    next() {
      this.step++;
    },
    nextsymptom() {
      if (this.symptomstep < this.symptoms.length - 1) {
        this.symptomstep++;
      } else {
        this.step++;
      }
    },
    prevsymptom() {
      if (this.symptomstep > 0) {
        this.symptomstep--;
      } else {
        this.step--;
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

      this.$http
        .post(
          "https://aiulvempz3.execute-api.eu-central-1.amazonaws.com/production/droplet",
          droplet
        )
        .then(response => {}, response => {});
      this.next();
    }
  }
});

Vue.component("forward-button", {
  template:
    '<at-button>Weiter<i class="icon icon-chevron-right"></i></at-button>'
});

Vue.component("back-button", {
  template:
    '<at-button><i class="icon icon-chevron-left"></i>Zurück</at-button>'
});
