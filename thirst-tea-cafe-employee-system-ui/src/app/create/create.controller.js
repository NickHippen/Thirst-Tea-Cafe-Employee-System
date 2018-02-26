export default class {

  constructor($log, CreateService) {
    'ngInject';
    angular.extend(this, {$log, CreateService});
  }

}
