import viewConfig from './view.config';
import view from './view.component';
import ViewService from './view.service';

export default angular.module('tt-employee.view', [
  'ui.bootstrap.datepickerPopup'
])
.config(viewConfig)
.component('view', view)
.service('ViewService', ViewService)
.name;
