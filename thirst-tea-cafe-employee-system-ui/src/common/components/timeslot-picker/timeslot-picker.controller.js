import _ from 'lodash';

/**
 * A controller for the timeslot-picker component
 * @module TimeslotPickerController
 */
export default class {

  constructor(TimeslotService) {
    'ngInject';
    angular.extend(this, {TimeslotService});
  }

  $onChanges() {
    this.date = this.TimeslotService.convertTimeslotToDate(this.timeslot);
  }

  /**
   * This will update the timeslot based on the current value of this.date
   * This will also update this.date based on the timeslot, effectively rounding the date down to the half-hour
   */
  updateTimeslot() {
    if (_.isNil(this.date)) {
      return;
    }
    this.timeslot = this.TimeslotService.convertDateToTimeslot(this.date);
    this.date = this.TimeslotService.convertTimeslotToDate(this.timeslot); // Make the date get corrected if typed a date that isn't on the hour or half-hour
  }

}
