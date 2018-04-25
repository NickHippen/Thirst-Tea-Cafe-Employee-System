import moment from 'moment';

/**
 * A service for handling schedules
 * @module AlertHandler
 */
export default class {

  constructor($http, REST_CONSTANTS) {
    'ngInject';
    angular.extend(this, {$http, REST_CONSTANTS});
  }

  /**
   * Gets the schedule corresponding to the date from backend
   * @param {Date} date
   */
  getSchedule(date) {
    if (!date) {
      date = new Date();
    }
    return this.$http({
      method: 'GET',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/schedule?date=${moment(date).format('YYYY-MM-DD')}`
    });
  }

  /**
   * Generates a new schedule for the date
   * @param {Date} date 
   */
  generateSchedule(date) {
    if (!date) {
      date = new Date();
    }
    return this.$http({
      method: 'GET',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/schedule/generate?date=${moment(date).format('YYYY-MM-DD')}`
    });
  }

  /**
   * Publishes a schedule for the date
   * @param {Date} date 
   */
  publishSchedule(date, schedule) {
    if (!date) {
      date = new Date();
    }
    return this.$http({
      method: 'POST',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/schedule?date=${moment(date).format('YYYY-MM-DD')}`,
      data: angular.toJson(schedule)
    });
  }

  /**
   * Deletes a schedule for the date
   * @param {Date} date 
   */
  deleteSchedule(date) {
    return this.$http({
      method: 'DELETE',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/schedule?date=${moment(date).format('YYYY-MM-DD')}`
    });
  }

  /**
   * Updates & publishes a schedule for the date
   * @param {Date} date 
   */
  updateSchedule(date, schedule) {
    return this.$http({
      method: 'PUT',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/schedule?date=${moment(date).format('YYYY-MM-DD')}`,
      data: angular.toJson(schedule)
    });
  }

}
