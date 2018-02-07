export default class {

  constructor($stateParams, AlertHandler, UserService, SongViewService, LoadingService) {
    'ngInject';
    angular.extend(this, {$stateParams, AlertHandler, UserService, SongViewService, LoadingService});
  }

  canEdit(score) {
    if (!this.UserService.isLoggedIn()) {
      return false;
    }
    if (this.UserService.userName !== score.name) {
      return false;
    }
    return true;
  }

}
