import Login from './login';
import SongSearch from './song-search';
import SongView from './song-view';
import UserSearch from './user-search';
import UserView from './user-view';

import app from './app.component';
import AppService from './app.service';

export default angular.module('tt-employee.app', [
  'ui.bootstrap.modal',
  Login,
  SongSearch,
  SongView,
  UserSearch,
  UserView
])
.component('app', app)
.service('AppService', AppService)
.name;
