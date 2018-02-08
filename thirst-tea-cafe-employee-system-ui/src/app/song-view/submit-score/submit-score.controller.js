export default class {

  constructor($log, $state, $stateParams, AlertHandler, UserService, SongViewService, LoadingService, $uibModalInstance, songId) {
    'ngInject';
    angular.extend(this, {$log, $state, $stateParams, AlertHandler, UserService, SongViewService, LoadingService, $uibModalInstance, songId});
    this.scoreForm = {songId};
  }

  submitScore() {
    this.SongViewService.submitScore(this.scoreForm);
    this.$uibModalInstance.close();
  }

}
