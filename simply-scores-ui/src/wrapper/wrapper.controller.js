export default class {

  constructor(AlertHandler, UserService, LoadingService) {
    'ngInject';
    angular.extend(this, {AlertHandler, UserService, LoadingService});
    UserService.checkCookiesForCredentials();
  }

  globalAlerts() {
    return this.AlertHandler.alerts;
  }

  closeAlert(index) {
    this.AlertHandler.removeAlert(index);
  }

  getUserName() {
    return this.UserService.userName;
  }

  isLoggedIn() {
    return this.UserService.isLoggedIn();
  }

  logout() {
    this.UserService.logout();
  }

  isLoading() {
    return this.LoadingService.loading;
  }

}