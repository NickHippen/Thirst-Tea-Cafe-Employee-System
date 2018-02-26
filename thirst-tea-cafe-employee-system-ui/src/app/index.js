import Login from './login';
import View from './view';
import Create from './create';

import app from './app.component';
import AppService from './app.service';

export default angular.module('tt-employee.app', [
  'ui.bootstrap.modal',
  Login,
  View,
  Create
])
.component('app', app)
.service('AppService', AppService)
.name;
