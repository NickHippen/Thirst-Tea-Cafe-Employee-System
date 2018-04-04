import AddUserCtrl from './add-user/add-user.controller';
import addUserTemplate from './add-user/add-user.html';

export default class {

  constructor($log, $uibModal, $state, AdminService, LoadingService) {
    'ngInject';
    angular.extend(this, {$log, $uibModal, $state, AdminService, LoadingService});
    
    this.LoadingService.loading = true;
    this.AdminService.getAllEmployees()
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
    
  deleteEmployee(employeeId) {
    this.LoadingService.loading = true;
    this.AdminService.deleteEmployee(employeeId)
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

  openAddUserModal(employee) {
    this.$uibModal.open({
      animation: true,
      template: addUserTemplate,
      controller: AddUserCtrl,
      controllerAs: '$ctrl',
      resolve: {
        employee: () => employee
      }
    });
  }

}
