import AddUserCtrl from './add-user/add-user.controller';
import addUserTemplate from './add-user/add-user.html';

/**
 * A controller for the admin component
 * @module AdminController
 */
export default class {

  constructor($log, $uibModal, $state, EmployeeService, LoadingService) {
    'ngInject';
    angular.extend(this, {$log, $uibModal, $state, EmployeeService, LoadingService});
    
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
   * Deletes an employee
   * @param {Number} employeeId 
   */
  deleteEmployee(employeeId) {
    this.LoadingService.loading = true;
    this.EmployeeService.deleteEmployee(employeeId)
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
   * Opens the add-user modal
   * @param {*} employee
   * @see AddUserController
   */
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
