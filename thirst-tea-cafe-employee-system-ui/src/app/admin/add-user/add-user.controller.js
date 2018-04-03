export default class {

  constructor($uibModalInstance, $state, LoadingService, AdminService, test) {
    'ngInject';
    angular.extend(this, {$uibModalInstance, $state, LoadingService, AdminService, test});
    this.employee = {};
  }
  
  close() {
    this.$uibModalInstance.close();
  }

  createEmployee() {
    this.LoadingService.loading = true;
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
