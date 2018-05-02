/**
 * A controller for the shiftMgmt component
 * @module ShiftMgmtController
 */
export default class {

  constructor($log, $state, AlertHandler, ShiftService, TimeslotService, LoadingService, DAY_OF_WEEK) {
    'ngInject';
    angular.extend(this, {$log, $state, AlertHandler, ShiftService, TimeslotService, LoadingService, DAY_OF_WEEK});

    this.LoadingService.loading = true;
    this.ShiftService.getAllShifts()
      .then(response => {
        this.LoadingService.loading = false;
        this.shifts = response.data;
        this.groupedShifts = this.ShiftService.groupShiftsByDay(this.shifts);
        console.log(this.groupedShifts);
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

  /**
   * Sends a request to the back end to add a shift for the day of the week
   * @param {*} dow the day of the week
   */
  addShift(dow) {
    this.newShift[dow].dayOfWeek = dow;
    
    this.LoadingService.loading = true;
    this.ShiftService.createShift(this.newShift[dow])
      .then(() => {
        this.LoadingService.loading = false;
        this.$state.reload();
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

  /**
   * Sends a request to the back end to delete a shift for the day of the week
   * @param {*} dow the day of the week 
   * @param {*} index the index of the shift to delete
   */
  deleteShift(dow, index) {
    this.LoadingService.loading = true;
    this.ShiftService.deleteShift(this.groupedShifts[dow][index].id)
      .then(() => {
        this.LoadingService.loading = false;
        this.$state.reload();
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

}
