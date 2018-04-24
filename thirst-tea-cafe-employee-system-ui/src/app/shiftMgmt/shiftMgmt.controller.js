export default class {

  constructor($log, ShiftService, TimeslotService, LoadingService, DAY_OF_WEEK) {
    'ngInject';
    angular.extend(this, {$log, ShiftService, TimeslotService, LoadingService, DAY_OF_WEEK});

    this.LoadingService.loading = true;
    this.ShiftService.getAllShifts()
      .then(response => {
        this.LoadingService.loading = false;
        this.shifts = response.data;
        this.groupedShifts = this.ShiftService.groupShiftsByDay(this.shifts);
        this.newShift = {};
      })
      .catch(error => {
        this.LoadingService.loading = false;
        let message;
        if (error.data && error.data.message) {
          message = error.data.message;
        } else {
          message = 'An unknown error occurred';
        }
        this.AlertHandler.error(message);
      });
  }

  addShift(dow) {
    console.log('Add:', dow);
  }

  deleteShift(dow, index) {
    console.log('Delete:', dow, index);
  }

}
