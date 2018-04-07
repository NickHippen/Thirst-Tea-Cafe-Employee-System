import moment from 'moment';

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

  quickAvailabilityString(dow) {
    if (!this.selectedEmployee || !this.selectedEmployee.availability[dow]) {
      return;
    }
    console.log(this.selectedEmployee.availability[dow]);
    return this.selectedEmployee.availability[dow].map(availability => {
      return moment(this.TimeslotService.convertTimeslotToDate(availability.fromTimeslot)).format('HH:mm') + '-' + moment(this.TimeslotService.convertTimeslotToDate(availability.toTimeslot)).format('HH:mm');
    }).join(', ');
  }

  selectEmployee(employee) {
    this.selectedEmployee = employee;
    this.clearFields();
  }

  clearFields() {
    this.newShift = {};
  }

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
