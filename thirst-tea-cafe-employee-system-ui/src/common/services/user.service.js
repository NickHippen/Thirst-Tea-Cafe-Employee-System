export default class {

  constructor($cookies) {
    'ngInject';
    angular.extend(this, {$cookies});
  }

  get userName() {
    return this._userName;
  }

  get password() {
    return this._password;
  }

  get credentials() {
    return {
      'userName': this.userName,
      'password': this.password
    };
  }

  newLogin(userName, password) {
    this._userName = userName;
    this._password = password;
    this.$cookies.put('userName', userName);
    this.$cookies.put('password', password);
  }

  checkCookiesForCredentials() {
    const userName = this.$cookies.get('userName');
    const password = this.$cookies.get('password');
    if (userName && password) {
      this.newLogin(userName, password);
    }
  }

  isLoggedIn() {
    return angular.isDefined(this.userName) && angular.isDefined(this.password);
  }

  logout() {
    this._userName = undefined;
    this._password = undefined;
    this.$cookies.remove('userName');
    this.$cookies.remove('password');
  }

}
