export default class {

  constructor($http, REST_CONSTANTS) {
    'ngInject';
    angular.extend(this, {$http, REST_CONSTANTS});
  }

  getSong(songId) {
    return this.$http({
      method: 'GET',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/song/${songId}`
    });
  }

  submitScore(scoreForm) {
    return scoreForm;
  }

}
