import moment from 'moment';

/**
 * A controller for the empMgmt component
 * @module EmpMgmtController
 */
export default class {

  constructor($state, $log, AlertHandler, TimeslotService, EmployeeService, LoadingService, DAY_OF_WEEK) {
    'ngInject';
    angular.extend(this, {$state, $log, AlertHandler, TimeslotService, EmployeeService, LoadingService, DAY_OF_WEEK});

    this.LoadingService.loading = true;
    this.EmployeeService.getAllEmployees()
      .then(response => {
        this.LoadingService.loading = false;
        this.employees = response.data;
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
   * Gets a display string for the currently selected employee for the day of the week specified
   * @param {*} dow the day of the week
   */
  quickAvailabilityString(dow) {
    if (!this.selectedEmployee || !this.selectedEmployee.availability[dow]) {
      return;
    }
    console.log(this.selectedEmployee.availability[dow]);
    return this.selectedEmployee.availability[dow].map(availability => {
      return moment(this.TimeslotService.convertTimeslotToDate(availability.fromTimeslot)).format('HH:mm') + '-' + moment(this.TimeslotService.convertTimeslotToDate(availability.toTimeslot)).format('HH:mm');
    }).join(', ');
  }

  /**
   * Selects an employee
   * @param {*} employee 
   */
  selectEmployee(employee) {
    this.selectedEmployee = employee;
    this.clearFields();
  }

  /**
   * Clears all new shift fields
   */
  clearFields() {
    this.newShift = {};
  }

  /**
   * Sends a request to the back end to add an availability for the currently selected employee for the day of the week
   * @param {*} dow the day of the week
   */
  addAvailabilityToSelected(dow) {
    const availability = {};
    availability[dow] = [
      {
        fromTimeslot: this.newShift[dow].startTimeslot,
        toTimeslot: this.newShift[dow].endTimeslot
      }
    ];
    this.LoadingService.loading = true;
    this.EmployeeService.addAvailability(this.selectedEmployee.employeeId, availability)
      .then(response => {
        this.LoadingService.loading = false;
        this.selectedEmployee.availability = response.data.availability;
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
   * Sends a request to the back end to delete an availability from the currently selected employee
   * @param {*} availability 
   */
  deleteAvailability(availability) {
    this.LoadingService.loading = true;
    this.EmployeeService.deleteAvailability(this.selectedEmployee.employeeId, availability.availabilityId)
      .then(response => {
        this.LoadingService.loading = false;
        this.selectedEmployee.availability = response.data.availability;
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
