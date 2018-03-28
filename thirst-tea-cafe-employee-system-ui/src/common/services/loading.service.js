/**
 * A service for handling page loading
 * @module LoadingService
 */
export default class {

  constructor() {
    'ngInject';
    angular.extend(this, {});
  }

  /**
   * Used to determine whether or not the page is currently loading
   */
  get loading() {
    return this._loading;
  }

  set loading(value) {
    this._loading = value;
  }

}
