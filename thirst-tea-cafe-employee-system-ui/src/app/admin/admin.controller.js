import AddUserCtrl from './add-user/add-user.controller';
import addUserTemplate from './add-user/add-user.html';

export default class {

  constructor($log, $uibModal, AdminService) {
    'ngInject';
    angular.extend(this, {$log, $uibModal, AdminService});
      
    this.users = [];
    // TEST CASES
    this.users.push({
      name: 'Mitch Huston',
      id: 'mitchellhuston'
    });
    this.users.push({
      name: 'Vincent Nguyen',
      id: 'vlnguyen'
    });
    // TEST CASES END
  }
    
  removeUser(userId) {
    this.AdminService.removeUser(userId);
  }

  openAddUserModal() {
    const someVarToPass = 'This message comes from the admin controller.';
    this.$uibModal.open({
      animation: true,
      template: addUserTemplate,
      controller: AddUserCtrl,
      controllerAs: '$ctrl',
      resolve: {
        test: () => someVarToPass
      }
    });
  }

}
