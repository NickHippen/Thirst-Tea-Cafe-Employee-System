import loginConfig from './login.config';
import login from './login.component';
import LoginService from './login.service';

export default angular.module('tt-employee.login', [])
.config(loginConfig)
.component('login', login)
.service('LoginService', LoginService)
.name;
