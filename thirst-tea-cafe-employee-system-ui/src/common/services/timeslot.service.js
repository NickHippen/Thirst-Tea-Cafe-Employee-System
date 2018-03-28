/**
 * A service for handling timeslot numbers
 * @module TimeslotService
 */
export default class {

  constructor() {
    'ngInject';
    angular.extend(this, {});
  }

  /**
   * Converts a date object into a timeslot. Will round down.
   * Only the time matters on the date object.
   * @param {Date} date the date object to convert to timeslot
   */
  convertDateToTimeslot(date) {
    return Math.floor((date.getHours() * 2) + (date.getMinutes() / 30));
  }

  /**
   * Converts a timeslot number into a date object.
   * The date will be today and the time will be converted from the timeslot. Will round down.
   * @param {Number} timeslot the number representing a timeslot for the day
   */
  convertTimeslotToDate(timeslot) {
    const date = new Date();
    date.setHours(Math.floor(timeslot / 2));
    date.setMinutes((timeslot % 2) * 30);
    return date;
  }

}
