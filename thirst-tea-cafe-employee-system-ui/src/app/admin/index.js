import adminConfig from './admin.config';
import admin from './admin.component';

export default angular.module('tt-employee.admin', [])
.config(adminConfig)
.component('admin', admin)
.name;
