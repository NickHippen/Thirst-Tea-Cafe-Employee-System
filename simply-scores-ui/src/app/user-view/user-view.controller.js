export default class {

  constructor($log, $state, $stateParams, AlertHandler, UserService, UserViewService, LoadingService) {
    'ngInject';
    angular.extend(this, {$log, $state, $stateParams, AlertHandler, UserService, UserViewService, LoadingService});
    this.userId = $stateParams.userId;
    this.getUser();
  }

  getUser() {
    this.LoadingService.loading = true;
    this.UserViewService.getUser(this.userId)
      .then(response => {
        this.LoadingService.loading = false;
        this.userData = response.data;
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
