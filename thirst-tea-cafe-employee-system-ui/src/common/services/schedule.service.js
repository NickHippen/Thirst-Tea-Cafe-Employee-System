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
   * Gets the newest schedule from backend
   */
  getSchedule() {
    return this.$http({
      method: 'GET',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/getSchedule`
    });
  }
}
