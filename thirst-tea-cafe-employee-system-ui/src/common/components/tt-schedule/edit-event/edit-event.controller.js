export default class {

  constructor($uibModalInstance, $state, LoadingService, ScheduleService, event) {
    'ngInject';
    angular.extend(this, {$uibModalInstance, $state, LoadingService, ScheduleService, event});
    if (this.event) {
      this.update = true;
    }
    // this.employee = {};
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
