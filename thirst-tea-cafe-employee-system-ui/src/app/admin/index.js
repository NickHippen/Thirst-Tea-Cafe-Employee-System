import adminConfig from './admin.config';
import admin from './admin.component';
import AdminService from './admin.service';

export default angular.module('tt-employee.admin', [])
.config(adminConfig)
.component('admin', admin)
.service('AdminService', AdminService)
.name;
