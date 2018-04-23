import ttScheduleTemplate from './tt-schedule.html';
import ttScheduleCtrl from './tt-schedule.controller';

export default {
  template: ttScheduleTemplate,
  controller: ttScheduleCtrl,
  bindings: {
    edit: '<',
    schedule: '<'
  }
};
