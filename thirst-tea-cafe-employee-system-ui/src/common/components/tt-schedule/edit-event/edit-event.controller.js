export default class {

  constructor($uibModalInstance, $state, LoadingService, ScheduleService, TimeslotService, EmployeeService, DAY_OF_WEEK, event) {
    'ngInject';
    angular.extend(this, {$uibModalInstance, $state, LoadingService, ScheduleService, TimeslotService, EmployeeService, DAY_OF_WEEK, event});
    if (this.event) {
      this.update = true;
    }
    // this.employee = {};
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
      
    this.startTimeslot = this.event ? 
      this.TimeslotService.convertDateToTimeslot(this.event.calendarEvent.startsAt) : null;
    this.endTimeslot = this.event ? 
      this.TimeslotService.convertDateToTimeslot(this.event.calendarEvent.endsAt) : null;
  }
  
  close() {
    this.$uibModalInstance.close();
  }

  modifySchedule() {
    this.LoadingService.loading = true;
    if (this.update) {
      this.ScheduleService.updateScheduleShift(this.event)
        .then(() => {
          this.LoadingService.loading = false;
          this.$uibModalInstance.close();
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
          this.$uibModalInstance.close();
          this.$state.reload();
        });
    } else {
      this.EmployeeService.createScheduleShift(this.event)
        .then(() => {
          this.LoadingService.loading = false;
          this.$uibModalInstance.close();
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
          this.$uibModalInstance.close();
          this.$state.reload();
        });
    }
  }
}
