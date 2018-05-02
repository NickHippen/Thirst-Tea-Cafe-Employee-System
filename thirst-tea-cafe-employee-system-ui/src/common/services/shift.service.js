/**
 * A service for handling shifts
 * @module ShiftService
 */
export default class {

  constructor($http, REST_CONSTANTS) {
    'ngInject';
    angular.extend(this, {$http, REST_CONSTANTS});
  }

  /**
   * Sends a request to the back end to delete a shift
   * @param {*} shiftId 
   * @returns a promise that will resolve with the response data
   */
  deleteShift(shiftId) {
    return this.$http({
      method: 'DELETE',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/shift/${shiftId}`
    });
  }

  /**
   * Sends a request to the back end to get a shift
   * @param {*} shiftId 
   * @returns a promise that will resolve with the response data
   */
  getShift(shiftId) {
    return this.$http({
      method: 'GET',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/shift/${shiftId}`
    });
  }

  /**
   * Sends a request to the back end to get all shifts
   * @returns a promise that will resolve with the response data
   */
  getAllShifts() {
    return this.$http({
      method: 'GET',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/shift`
    });
  }

  /**
   * Sends a request to the back end to create a shift
   * @param {*} shift 
   * @returns a promise that will resolve with the response data
   */
  createShift(shift) {
    return this.$http({
      method: 'POST',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/shift`,
      data: angular.toJson(shift)
    });
  }

  /**
   * Groups all shifts in an object mapping them by their day of week
   * @param {*} shifts 
   * @returns a grouped shift object (essentially a dictionary/map)
   */
  groupShiftsByDay(shifts) {
    const groupedShifts = {};
    for (const shift of shifts) {
      if (angular.isUndefined(groupedShifts[shift.dayOfWeek])) {
        groupedShifts[shift.dayOfWeek] = [];
      }
      groupedShifts[shift.dayOfWeek].push(shift);
    }
    return groupedShifts;
  }
}
