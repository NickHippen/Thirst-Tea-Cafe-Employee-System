import empMgmtConfig from './empMgmt.config';
import empMgmt from './empMgmt.component';
import EmpMgmtService from './empMgmt.service';

import './empMgmt.scss';

export default angular.module('tt-employee.empMgmt', [])
.config(empMgmtConfig)
.component('empMgmt', empMgmt)
.service('EmpMgmtService', EmpMgmtService)
.name;
