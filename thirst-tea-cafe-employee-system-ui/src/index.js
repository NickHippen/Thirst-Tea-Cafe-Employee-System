import angular from 'angular';
import angularCookies from 'angular-cookies';
import 'angular-ui-router';
import bootstrap from 'angular-ui-bootstrap';

import routesConfig from './routes';

import Common from './common';
import App from './app';
import Wrapper from './wrapper';

import './index.scss';

export default angular.module('tt-employee', [
  'ui.router',
  angularCookies,
  bootstrap,
  Common,
  Wrapper,
  App
])
.config(routesConfig)
.name;
