import registerConfig from './register.config';
import register from './register.component';
import RegisterService from './register.service';

export default angular.module('simplyScores.login.register', [
])
.config(registerConfig)
.component('register', register)
.service('RegisterService', RegisterService)
.name;
