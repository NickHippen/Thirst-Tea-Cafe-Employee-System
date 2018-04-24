import angular from 'angular';
import angularCookies from 'angular-cookies';
import 'angular-ui-router';
import bootstrap from 'angular-ui-bootstrap';
import angularBootstrapCalendar from 'angular-bootstrap-calendar';
import '../node_modules/angular-bootstrap-calendar/dist/css/angular-bootstrap-calendar.min.css';

import routesConfig from './routes';
import calendarConfig from './calendar';

import Common from './common';
import App from './app';
import Wrapper from './wrapper';

import './index.scss';

export default angular.module('tt-employee', [
  'ui.router',
  angularCookies,
  bootstrap,
  angularBootstrapCalendar,
  Common,
  Wrapper,
  App
])
.config(routesConfig)
.config(calendarConfig)
.run($transitions => {
  $transitions.onStart({}, trans => {
    const UserService = trans.injector().get('UserService');
    if (!UserService.isLoggedIn() && trans.targetState().name() !== 'login') {
      trans.router.stateService.go('login');
    }
  });
})
.name;
