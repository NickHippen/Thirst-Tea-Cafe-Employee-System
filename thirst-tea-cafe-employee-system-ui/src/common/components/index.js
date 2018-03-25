import ttSchedule from './tt-schedule/tt-schedule.component';
import timeslotPicker from './timeslot-picker/timeslot-picker.component';

export default angular.module('tt-employee.common.components', [
  'ui.bootstrap.dateparser'
])
.component('ttSchedule', ttSchedule)
.component('timeslotPicker', timeslotPicker)
.name;
