import ForumRest from './forum-rest.service';
import AlertHandler from './alert-handler.service';
import UserService from './user.service';
import LoadingService from './loading.service';

export default angular.module('tt-employee.common.services', [
])
.service('ForumRest', ForumRest)
.service('AlertHandler', AlertHandler)
.service('UserService', UserService)
.service('LoadingService', LoadingService)
.name;
