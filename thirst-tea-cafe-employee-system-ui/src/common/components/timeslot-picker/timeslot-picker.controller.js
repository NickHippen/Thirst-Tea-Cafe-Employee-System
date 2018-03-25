import _ from 'lodash';

export default class {

  constructor(TimeslotService) {
    'ngInject';
    angular.extend(this, {TimeslotService});
  }

  $onChanges() {
    this.date = this.TimeslotService.convertTimeslotToDate(this.timeslot);
  }

  updateTimeslot() {
    if (_.isNil(this.date)) {
      return;
    }
    this.timeslot = this.TimeslotService.convertDateToTimeslot(this.date);
  }

}
