export default class {

  constructor($http, REST_CONSTANTS) {
    'ngInject';
    angular.extend(this, {$http, REST_CONSTANTS});
  }

  deleteShift(shiftId) {
    return this.$http({
      method: 'DELETE',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/shift/${shiftId}`
    });
  }

  getShift(shiftId) {
    return this.$http({
      method: 'GET',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/shift/${shiftId}`
    });
  }

  getAllShifts() {
    return this.$http({
      method: 'GET',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/shift`
    });
  }

  createShift(shift) {
    return this.$http({
      method: 'POST',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/shift`,
      data: angular.toJson(shift)
    });
  }

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
