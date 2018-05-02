/**
 * A controller for the add-user modal
 * @module AddUserController
 */
export default class {

  constructor($uibModalInstance, $state, LoadingService, EmployeeService, employee) {
    'ngInject';
    angular.extend(this, {$uibModalInstance, $state, LoadingService, EmployeeService, employee});
    if (this.employee) {
      this.update = true;
    }
    // this.employee = {};
  }
  
  /**
   * Closes the modal
   */
  close() {
    this.$uibModalInstance.close();
  }

  /**
   * Applies the employee modifications & sends to back end. Will reload upon completion.
   */
  modifyEmployee() {
    this.LoadingService.loading = true;
    if (this.update) {
      this.EmployeeService.updateEmployee(this.employee)
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
      this.EmployeeService.createEmployee(this.employee)
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
