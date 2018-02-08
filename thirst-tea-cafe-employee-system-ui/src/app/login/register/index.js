import registerConfig from './register.config';
import register from './register.component';
import RegisterService from './register.service';

export default angular.module('tt-employee.login.register', [
])
.config(registerConfig)
.component('register', register)
.service('RegisterService', RegisterService)
.name;
