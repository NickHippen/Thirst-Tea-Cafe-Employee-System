import timeslotPickerTemplate from './timeslot-picker.html';
import TimeslotPickerCtrl from './timeslot-picker.controller';

/**
 * A component representing a text input field that models an inputted time directly into a timeslot
 * @module timeslot-picker
 */
export default {
  template: timeslotPickerTemplate,
  controller: TimeslotPickerCtrl,
  bindings: {
    timeslot: '='
  }
};
