/**
 * A service for handling users
 * @module UserService
 */
export default class {

  constructor($state, $cookies, LoginService, LoadingService, AlertHandler) {
    'ngInject';
    angular.extend(this, {$state, $cookies, LoginService, LoadingService, AlertHandler});
  }

  get username() {
    return this._username;
  }

  get password() {
    return this._password;
  }

  get credentials() {
    return {
      'username': this.username,
      'password': this.password
    };
  }

  /**
   * Locally stores a new login
   * @param {string} username 
   * @param {string} password 
   * @param {*} employee 
   */
  newLogin(username, password, employee) {
    this._username = username;
    this._password = password;
    this.employee = employee;
    this.$cookies.put('username', username);
    this.$cookies.put('password', password);
  }

  /**
   * Checks cookies for credentials and validates, redirects to home if successful
   */
  checkCookiesForCredentials() {
    const username = this.$cookies.get('username');
    const password = this.$cookies.get('password');
    if (username && password) {
      this.LoadingService.loading = true;
      this.LoginService.login({username, password})
        .then(response => {
          this.LoadingService.loading = false;
          this.newLogin(username, password, response.data);
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

  /**
   * Checks whether or not the client is logged in
   * @returns whether or not user is logged in
   */
  isLoggedIn() {
    return angular.isDefined(this.username) && angular.isDefined(this.password);
  }

  /**
   * Logs the client out
   */
  logout() {
    this._username = undefined;
    this._password = undefined;
    this.$cookies.remove('username');
    this.$cookies.remove('password');
    this.$state.go('login');
  }

}
