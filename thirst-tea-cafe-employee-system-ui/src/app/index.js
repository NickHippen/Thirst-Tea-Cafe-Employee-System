import Login from './login';
import View from './view';
import Create from './create';
import EmpMgmt from './empMgmt';
import ShiftMgmt from './shiftMgmt';
import Admin from './admin';

import app from './app.component';
import AppService from './app.service';

export default angular.module('tt-employee.app', [
  'ui.bootstrap.modal',
  'ui.bootstrap.accordion',
  Login,
  View,
  Create,
  EmpMgmt,
  ShiftMgmt,
  Admin
])
.component('app', app)
.service('AppService', AppService)
.name;
