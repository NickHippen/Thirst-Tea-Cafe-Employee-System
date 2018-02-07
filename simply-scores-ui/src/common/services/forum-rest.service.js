export default class {

  constructor($http, RestConstants) {
    'ngInject';
    angular.extend(this, {$http, RestConstants});
  }

  getPosts() {
    return this.$http({
      method: 'GET',
      url: `${this.RestConstants.BASE_URL}:${this.RestConstants.PORT}`
    });
  }

}
