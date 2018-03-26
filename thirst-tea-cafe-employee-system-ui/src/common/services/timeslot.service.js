export default class {

  constructor() {
    'ngInject';
    angular.extend(this, {});
  }

  convertDateToTimeslot(date) {
    return Math.floor((date.getHours() * 2) + (date.getMinutes() / 30));
  }

  convertTimeslotToDate(timeslot) {
    const date = new Date();
    date.setHours(Math.floor(timeslot / 2));
    date.setMinutes((timeslot % 2) * 30);
    return date;
  }

}
