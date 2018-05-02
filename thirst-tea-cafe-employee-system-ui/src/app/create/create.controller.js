import moment from 'moment';

/**
 * A controller for the create component
 * @module CreateController
 */
export default class {

  constructor($log, $window, $state, CreateService, ScheduleService, LoadingService, AlertHandler) {
    'ngInject';
    angular.extend(this, {$log, $window, CreateService, ScheduleService, LoadingService, AlertHandler});
    this.selectedDate = moment().toDate();
    this.dateOptions = {
      dateDisabled: false,
      formatYear: 'yy',
      // maxDate: moment().add(1, 'week').endOf('isoWeek'),
      minDate: moment().startOf('isoWeek'),
      startingDay: 1
    };
  }

  /**
   * Sends a request to backend to generate a new schedule and receive the object back
   */
  generateSchedule() {
    this.LoadingService.loading = true;
    this.ScheduleService.generateSchedule(this.selectedDate)
      .then(response => {
        this.schedule = response.data;
        this.LoadingService.loading = false;
        this.showSchedule = true;
      })
      .catch(error => {
        let message;
        if (error.data && error.data.message) {
          message = error.data.message;
        } else {
          message = 'An unknown error occurred';
        }
        this.AlertHandler.error(message);
        this.LoadingService.loading = false;
      });
  }

  /**
   * Sends a request to the back end to publish the schedule
   */
  publishSchedule() {
    this.LoadingService.loading = true;
    this.ScheduleService.publishSchedule(this.selectedDate, this.schedule)
      .then(() => {
        this.LoadingService.loading = false;
        this.$window.location.reload(true);
      })
      .catch(error => {
        let message;
        if (error.data && error.data.message) {
          message = error.data.message;
        } else {
          message = 'An unknown error occurred';
        }
        this.AlertHandler.error(message);
        this.LoadingService.loading = false;
      });
  }

  /**
   * Sends a request to the back end to update the schedule
   */
  updateSchedule() {
    this.LoadingService.loading = true;
    this.ScheduleService.updateSchedule(this.selectedDate, this.schedule)
      .then(() => {
        this.LoadingService.loading = false;
        this.$window.location.reload(true);
      })
      .catch(error => {
        let message;
        if (error.data && error.data.message) {
          message = error.data.message;
        } else {
          message = 'An unknown error occurred';
        }
        this.AlertHandler.error(message);
        this.LoadingService.loading = false;
      });
  }

  /**
   * Sends a request to backend to receive an existing schedule back
   */
  editExistingSchedule() {
    this.LoadingService.loading = true;
    this.ScheduleService.getSchedule(this.selectedDate)
      .then(response => {
        this.update = true;
        this.schedule = response.data;
        this.LoadingService.loading = false;
        this.showSchedule = true;
      })
      .catch(error => {
        let message;
        if (error.data && error.data.message) {
          message = error.data.message;
        } else {
          message = 'An unknown error occurred';
        }
        this.AlertHandler.error(message);
        this.LoadingService.loading = false;
      });

    // this.schedule = {
    //   'days': {
    //     'MONDAY': {
    //       'scheduledTimeslots': {
    //         '22': [{'firstName': 'Vincent N'}],
    //         '23': [{'firstName': 'Vincent N'}],
    //         '24': [{'firstName': 'Vincent N'}],
    //         '25': [{'firstName': 'Vincent N'}],
    //         '26': [{'firstName': 'Vincent N'}],
    //         '27': [{'firstName': 'Vincent N'}],
    //         '28': [{'firstName': 'Vincent N'}],
    //         '29': [{'firstName': 'Vincent N'}],
    //         '30': [{'firstName': 'Nick H'}],
    //         '31': [{'firstName': 'Nick H'}],
    //         '32': [{'firstName': 'Nick H'}],
    //         '33': [{'firstName': 'Nick H'}],
    //         '34': [{'firstName': 'Mitch H'}],
    //         '35': [{'firstName': 'Mitch H'}],
    //         '36': [{'firstName': 'Mitch H'}],
    //         '37': [{'firstName': 'Mitch H'}],
    //         '38': [{'firstName': 'Hayden F'}],
    //         '39': [{'firstName': 'Hayden F'}],
    //         '40': [{'firstName': 'Hayden F'}],
    //         '41': [{'firstName': 'Hayden F'}]
    //       }
    //     },
    //     'TUESDAY': {
    //       'scheduledTimeslots': {
    //         '10': [{'firstName': 'Nick H'}]
    //       }
    //     }
    //   }
    // };
    // this.LoadingService.loading = false;
    // this.showSchedule = true;
  }

  /**
   * Opens the calendar
   */
  openCalendar() {
    this.calendarOpen = true;
  }

}
