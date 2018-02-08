export default class {

  constructor($http, REST_CONSTANTS) {
    'ngInject';
    angular.extend(this, {$http, REST_CONSTANTS});
  }

  searchUsers(userSearchData) {
    return this.$http({
      method: 'POST',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/userSearch`,
      data: angular.toJson(userSearchData)
    });
  }

}
