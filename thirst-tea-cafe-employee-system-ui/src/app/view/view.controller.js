import moment from 'moment';

/**
 * Controller for schedule view component
 * @module ViewController
 */
export default class {

  constructor($log, ViewService) {
    'ngInject';
    angular.extend(this, {$log, ViewService});
    this.selectedDate = moment().toDate();
    this.dateOptions = {
      dateDisabled: false,
      formatYear: 'yy',
      maxDate: moment().add(1, 'week').endOf('isoWeek'),
      minDate: moment().startOf('isoWeek'),
      startingDay: 1
    };
  }

  /**
   * Opens the calendar
   */
  openCalendar() {
    this.calendarOpen = true;
  }

  /**
   * Called when the calendar is changed
   */
  onCalendarChange() {
  }

}
