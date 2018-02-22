import Login from './login';

import app from './app.component';
import AppService from './app.service';

export default angular.module('tt-employee.app', [
  'ui.bootstrap.modal',
  Login
])
.component('app', app)
.service('AppService', AppService)
.name;
