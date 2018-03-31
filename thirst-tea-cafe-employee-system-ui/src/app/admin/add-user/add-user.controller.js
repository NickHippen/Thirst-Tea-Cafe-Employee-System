export default class {

  constructor($uibModalInstance, test) {
    'ngInject';
    angular.extend(this, {$uibModalInstance, test});
  }
  
  close() {
    this.$uibModalInstance.close();
  }
  
}
