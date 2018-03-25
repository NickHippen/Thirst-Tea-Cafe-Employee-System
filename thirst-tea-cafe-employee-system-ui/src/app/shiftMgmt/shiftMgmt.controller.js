export default class {

  constructor($log, ShiftMgmtService, DAY_OF_WEEK) {
    'ngInject';
    angular.extend(this, {$log, ShiftMgmtService, DAY_OF_WEEK});

    this.shifts = [{
      dayOfWeek: 'MONDAY',
      startTimeslot: 16,
      endTimeslot: 34,
      minEmployees: 2,
      maxEmployees: 2,
      adminOnly: false
    },
    {
      dayOfWeek: 'MONDAY',
      startTimeslot: 16,
      endTimeslot: 34,
      minEmployees: 1,
      maxEmployees: 1,
      adminOnly: true
    },
    {
      dayOfWeek: 'TUESDAY',
      startTimeslot: 16,
      endTimeslot: 34,
      minEmployees: 2,
      maxEmployees: 2,
      adminOnly: false
    },
    {
      dayOfWeek: 'WEDNESDAY',
      startTimeslot: 16,
      endTimeslot: 34,
      minEmployees: 2,
      maxEmployees: 2,
      adminOnly: false
    },
    {
      dayOfWeek: 'THURSDAY',
      startTimeslot: 16,
      endTimeslot: 34,
      minEmployees: 2,
      maxEmployees: 2,
      adminOnly: false
    },
    {
      dayOfWeek: 'FRIDAY',
      startTimeslot: 16,
      endTimeslot: 34,
      minEmployees: 2,
      maxEmployees: 2,
      adminOnly: false
    }];
    this.groupedShifts = this.ShiftMgmtService.groupShiftsByDay(this.shifts);
    this.newShift = {};
  }

  addShift(dow) {
    console.log('Add:', dow);
  }

  deleteShift(dow, index) {
    console.log('Delete:', dow, index);
  }

}
