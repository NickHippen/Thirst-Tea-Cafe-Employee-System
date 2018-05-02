/**
 * A service for handling login requests
 * @module LoginService
 */
export default class {

  constructor($http, REST_CONSTANTS) {
    'ngInject';
    angular.extend(this, {$http, REST_CONSTANTS});
  }

  /**
   * Sends a request to the back end to log the user in
   * @param {*} loginData 
   * @returns a promise that will resolve with the request response data
   */
  login(loginData) {
    return this.$http({
      method: 'POST',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/login`,
      data: angular.toJson(loginData)
    });
  }

}
