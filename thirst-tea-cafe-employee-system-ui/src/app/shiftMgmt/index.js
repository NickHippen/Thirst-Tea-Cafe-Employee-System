import shiftMgmtConfig from './shiftMgmt.config';
import shiftMgmt from './shiftMgmt.component';

import './shiftMgmt.scss';

export default angular.module('tt-employee.shiftMgmt', [])
.config(shiftMgmtConfig)
.component('shiftMgmt', shiftMgmt)
.name;
