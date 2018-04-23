/**
 * Controller for login component
 * @module LoginController
 */
export default class {

  constructor($log, $state, AlertHandler, UserService, LoginService, LoadingService) {
    'ngInject';
    angular.extend(this, {$log, $state, AlertHandler, UserService, LoginService, LoadingService});

    this.loginForm = {
    };
  }

  /**
   * Submits the login form
   */
  submitLoginForm() {
    this.LoadingService.loading = true;
    this.LoginService.login(this.loginForm)
      .then(response => {
        this.LoadingService.loading = false;
        this.UserService.newLogin(this.loginForm.username, this.loginForm.password, response.data);
        this.$state.go('home');
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

}
