export default class {

  constructor($log, $state, AlertHandler, UserService, LoginService, LoadingService) {
    'ngInject';
    angular.extend(this, {$log, $state, AlertHandler, UserService, LoginService, LoadingService});

    this.loginForm = {
    };
  }

  submitLoginForm() {
    this.LoadingService.loading = true;
    this.LoginService.login(this.loginForm)
      .then(() => {
        this.LoadingService.loading = false;
        this.UserService.newLogin(this.loginForm.userName, this.loginForm.password);
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
