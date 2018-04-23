export default class {

  constructor($log, ShiftService, DAY_OF_WEEK) {
    'ngInject';
    angular.extend(this, {$log, ShiftService, DAY_OF_WEEK});

    this.shifts = [{
      dayOfWeek: 'MONDAY',
      startTimeslot: 16,
      endTimeslot: 34,
      numEmployees: 2,
      maxEmployees: 2,
      admin: 0
    },
    {
      dayOfWeek: 'MONDAY',
      startTimeslot: 16,
      endTimeslot: 34,
      numEmployees: 1,
      maxEmployees: 1,
      admin: 1
    },
    {
      dayOfWeek: 'TUESDAY',
      startTimeslot: 16,
      endTimeslot: 34,
      numEmployees: 2,
      maxEmployees: 2,
      admin: 0
    },
    {
      dayOfWeek: 'WEDNESDAY',
      startTimeslot: 16,
      endTimeslot: 34,
      numEmployees: 2,
      maxEmployees: 2,
      admin: 0
    },
    {
      dayOfWeek: 'THURSDAY',
      startTimeslot: 16,
      endTimeslot: 34,
      numEmployees: 2,
      maxEmployees: 2,
      admin: 0
    },
    {
      dayOfWeek: 'FRIDAY',
      startTimeslot: 16,
      endTimeslot: 34,
      numEmployees: 2,
      maxEmployees: 2,
      admin: 0
    }];
    this.groupedShifts = this.ShiftService.groupShiftsByDay(this.shifts);
    this.newShift = {};
  }

  addShift(dow) {
    console.log('Add:', dow);
  }

  deleteShift(dow, index) {
    console.log('Delete:', dow, index);
  }

}
