export default class {

  constructor($http, REST_CONSTANTS) {
    'ngInject';
    angular.extend(this, {$http, REST_CONSTANTS});
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
