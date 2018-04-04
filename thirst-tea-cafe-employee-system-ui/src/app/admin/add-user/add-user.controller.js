export default class {

  constructor($uibModalInstance, $state, LoadingService, AdminService, employee) {
    'ngInject';
    angular.extend(this, {$uibModalInstance, $state, LoadingService, AdminService, employee});
    if (this.employee) {
      this.update = true;
    }
    // this.employee = {};
  }
  
  close() {
    this.$uibModalInstance.close();
  }

  modifyEmployee() {
    this.LoadingService.loading = true;
    if (this.update) {
      this.AdminService.updateEmployee(this.employee)
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
      this.AdminService.createEmployee(this.employee)
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
