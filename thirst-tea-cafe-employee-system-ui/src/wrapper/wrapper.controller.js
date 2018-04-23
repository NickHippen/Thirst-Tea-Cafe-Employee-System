/**
 * A controller for the wrapper component
 * @module WrapperController
 */
export default class {

  constructor(AlertHandler, UserService, LoadingService) {
    'ngInject';
    angular.extend(this, {AlertHandler, UserService, LoadingService});
    UserService.checkCookiesForCredentials();
  }

  /**
   * @returns all alerts
   */
  globalAlerts() {
    return this.AlertHandler.alerts;
  }

  /**
   * Closes an alert by index
   * @param {*} index 
   */
  closeAlert(index) {
    this.AlertHandler.removeAlert(index);
  }

  getUserName() {
    return this.UserService.userName;
  }

  /**
   * @returns whether or not the client is logged in
   */
  isLoggedIn() {
    return this.UserService.isLoggedIn();
  }
    
  /**
   * @returns whether or not the client is an admin
   */
  isAdmin() {
    return this.UserService.employee && this.UserService.employee.admin;
  }

  /**
   * Logs the client out
   */
  logout() {
    this.UserService.logout();
  }

  isLoading() {
    return this.LoadingService.loading;
  }

}
