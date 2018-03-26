import shiftMgmtConfig from './shiftMgmt.config';
import shiftMgmt from './shiftMgmt.component';
import ShiftMgmtService from './shiftMgmt.service';

import './shiftMgmt.scss';

export default angular.module('tt-employee.shiftMgmt', [])
.config(shiftMgmtConfig)
.component('shiftMgmt', shiftMgmt)
.service('ShiftMgmtService', ShiftMgmtService)
.name;
