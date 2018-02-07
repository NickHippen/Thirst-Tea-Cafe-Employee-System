import userViewConfig from './user-view.config';
import userView from './user-view.component';
import UserViewService from './user-view.service';

export default angular.module('simplyScores.userView', [
])
.config(userViewConfig)
.component('userView', userView)
.service('UserViewService', UserViewService)
.name;
