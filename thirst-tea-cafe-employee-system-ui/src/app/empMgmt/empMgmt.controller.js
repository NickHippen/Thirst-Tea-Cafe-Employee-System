export default class {

  constructor($log, EmpMgmtService, DAY_OF_WEEK) {
    'ngInject';
    angular.extend(this, {$log, EmpMgmtService, DAY_OF_WEEK});

    this.employees = [
      {
        employeeId: 1,
        name: 'Nick Hippen'
      },
      {
        employeeId: 2,
        name: 'Vincent Nguyen'
      },
      {
        employeeId: 3,
        name: 'Mitch Huston'
      },
      {
        employeeId: 4,
        name: 'Hayden Fields'
      }
    ];
  }

  selectEmployee(employee) {
    this.selectedEmployee = employee;
  }

}
