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
}
