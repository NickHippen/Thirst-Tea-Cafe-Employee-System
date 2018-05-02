/**
 * A controller for the edit-event modal
 * @module EditEventModal
 */
export default class {

  constructor($uibModalInstance, $state, LoadingService, ScheduleService, TimeslotService, EmployeeService, DAY_OF_WEEK, event) {
    'ngInject';
    angular.extend(this, {$uibModalInstance, $state, LoadingService, ScheduleService, TimeslotService, EmployeeService, DAY_OF_WEEK, event});
    this.editShift = {};
    if (this.event) {
      this.update = true;
      this.originalShift = {};
      this.originalShift.employee = event.calendarEvent.employee;
      this.originalShift.dow = event.calendarEvent.dow;
      this.originalShift.startTimeslot = this.TimeslotService.convertDateToTimeslot(this.event.calendarEvent.startsAt);
      this.originalShift.endTimeslot = this.TimeslotService.convertDateToTimeslot(this.event.calendarEvent.endsAt);
      this.editShift = angular.copy(this.originalShift, this.editShift);
    }
    console.log(this.event);
    this.EmployeeService.getAllEmployees()
      .then(response => {
        this.LoadingService.loading = false;
        this.allEmployees = response.data;
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
   * Closes the modal
   */
  close() {
    this.$uibModalInstance.close();
  }

  /**
   * Submits the specified changes & closes the modal
   */
  submit() {
    this.submitList = [];
    if (this.originalShift && !angular.equals(this.originalShift, this.editShift)) {
      this.originalShift.delete = true;
      this.submitList.push(this.originalShift);
    }
    this.submitList.push(this.editShift);
    this.$uibModalInstance.close(this.submitList);
    // this.LoadingService.loading = true;
    // if (this.update) {
    //   this.ScheduleService.updateScheduleShift(this.event)
    //     .then(() => {
    //       this.LoadingService.loading = false;
    //       this.$uibModalInstance.close();
    //       this.$state.reload();
    //     })
    //     .catch(error => {
    //       this.LoadingService.loading = false;
    //       let message;
    //       if (error.data && error.data.message) {
    //         message = error.data.message;
    //       } else {
    //         message = 'An unknown error occurred';
    //       }
    //       this.AlertHandler.error(message);
    //       this.$uibModalInstance.close();
    //       this.$state.reload();
    //     });
    // } else {
    //   this.EmployeeService.createScheduleShift(this.event)
    //     .then(() => {
    //       this.LoadingService.loading = false;
    //       this.$uibModalInstance.close();
    //       this.$state.reload();
    //     })
    //     .catch(error => {
    //       this.LoadingService.loading = false;
    //       let message;
    //       if (error.data && error.data.message) {
    //         message = error.data.message;
    //       } else {
    //         message = 'An unknown error occurred';
    //       }
    //       this.AlertHandler.error(message);
    //       this.$uibModalInstance.close();
    //       this.$state.reload();
    //     });
    // }
  }

  /**
   * Closes the modal with information specifying the deletion of the shift
   */
  delete() {
    this.editShift.delete = true;
    this.$uibModalInstance.close([this.editShift]);
  }

}
