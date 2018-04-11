import ForumRest from './forum-rest.service';
import AlertHandler from './alert-handler.service';
import UserService from './user.service';
import LoadingService from './loading.service';
import TimeslotService from './timeslot.service';
import EmployeeService from './employee.service';
import ScheduleService from './schedule.service';

export default angular.module('tt-employee.common.services', [
])
.service('ForumRest', ForumRest)
.service('AlertHandler', AlertHandler)
.service('UserService', UserService)
.service('LoadingService', LoadingService)
.service('TimeslotService', TimeslotService)
.service('EmployeeService', EmployeeService)
.service('ScheduleService', ScheduleService)
.name;
