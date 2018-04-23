export default class {

  constructor($log, AppService) {
    'ngInject';
    angular.extend(this, {$log, AppService});
  }

}
