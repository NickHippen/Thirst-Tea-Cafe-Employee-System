import submitScoreTemplate from './submit-score/submit-score.html';
import SubmitScoreCtrl from './submit-score/submit-score.controller';

export default class {

  constructor($log, $state, $stateParams, $uibModal, AlertHandler, UserService, SongViewService, LoadingService) {
    'ngInject';
    angular.extend(this, {$log, $state, $stateParams, $uibModal, AlertHandler, UserService, SongViewService, LoadingService});
    this.songId = $stateParams.songId;
    this.getSong();
    this.getScores();
  }

  getSong() {
    this.LoadingService.loading = true;
    this.SongViewService.getSong(this.songId)
      .then(response => {
        this.LoadingService.loading = false;
        this.songData = response.data;
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

  getScores() {
    this.scores = [
      {
        rank: 1,
        name: 'abc',
        score: '100.0%',
        date: '11/24/2017'
      }
    ];
  }

  openSubmitScoreModal() {
    this.$uibModal.open({
      animation: true,
      template: submitScoreTemplate,
      controller: SubmitScoreCtrl,
      controllerAs: 'ss',
      resolve: {
        songId: () => this.songId
      }
    });
    // this.$uibModal.open({
    //   animation: true,
    //   component: 'submitScore',
    //   resolve: {
    //     songId: () => this.songId
    //   }
    // });
  }

  isLoggedIn() {
    return this.UserService.isLoggedIn();
  }

}
