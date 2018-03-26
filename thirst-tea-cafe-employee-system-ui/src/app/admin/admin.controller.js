export default class {

  constructor($log, AdminService) {
    'ngInject';
    angular.extend(this, {$log, AdminService});
      
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
}
