import Register from './register';

import loginConfig from './login.config';
import login from './login.component';
import LoginService from './login.service';

export default angular.module('simplyScores.login', [
  Register
])
.config(loginConfig)
.component('login', login)
.service('LoginService', LoginService)
.name;
