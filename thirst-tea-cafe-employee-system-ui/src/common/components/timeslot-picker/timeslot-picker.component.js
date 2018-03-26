import timeslotPickerTemplate from './timeslot-picker.html';
import TimeslotPickerCtrl from './timeslot-picker.controller';

export default {
  template: timeslotPickerTemplate,
  controller: TimeslotPickerCtrl,
  bindings: {
    timeslot: '='
  }
};
