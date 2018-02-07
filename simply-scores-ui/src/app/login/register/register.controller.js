export default class {

  constructor($log, $state, AlertHandler, UserService, RegisterService, LoadingService) {
    'ngInject';
    angular.extend(this, {$log, $state, AlertHandler, UserService, RegisterService, LoadingService});

    this.registerForm = {
    };
  }

  submitRegisterForm() {
    if (!this.registerForm.userName || !this.registerForm.password) {
      this.AlertHandler.error('Username/password is required');
      return;
    }
    if (this.registerForm.password !== this.registerForm.confirmPassword) {
      this.AlertHandler.error('Passwords do not match');
      return;
    }
    this.LoadingService.loading = true;
    this.RegisterService.register(this.registerForm)
      .then(() => {
        this.LoadingService.loading = false;
        this.UserService.newLogin(this.registerForm.userName, this.registerForm.password);
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
