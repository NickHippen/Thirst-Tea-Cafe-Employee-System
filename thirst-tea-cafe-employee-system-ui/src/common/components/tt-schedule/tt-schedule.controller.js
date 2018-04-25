import moment from 'moment';
import _ from 'lodash';
import EditEventCtrl from './edit-event/edit-event.controller';
import editEventTemplate from './edit-event/edit-event.html';

export default class {

  constructor($window, $uibModal, TimeslotService, AlertHandler, ScheduleService, LoadingService, DAY_OF_WEEK) {
    'ngInject';
    angular.extend(this, {$window, $uibModal, TimeslotService, AlertHandler, ScheduleService, LoadingService, DAY_OF_WEEK});
  }

  $onChanges() {
    if (!this.schedule) {
      return;
    }
    this.reloadScheduleDisplay();
  }

  createEventsFromSchedule(weeklySchedule) {
    const events = [];
    const dow = this.DAY_OF_WEEK;

    for (const dayName of Object.keys(weeklySchedule.days)) {
      const dayVal = dow[dayName].offset;
      const dailySchedule = weeklySchedule.days[dayName];
      // This loop gives us all of the daily schedules
      const tempEvents = {};
      for (let timeslot of Object.keys(dailySchedule.scheduledTimeslots).sort()) {
        timeslot = parseInt(timeslot);
        const employees = dailySchedule.scheduledTimeslots[timeslot]; // List of employees scheduled for timeslot
        // This loop gives us the list of employees scheduled for each timeslot
        for (const employee of employees) {
          let event = null;
          if (employee.firstName in tempEvents) {
            event = tempEvents[employee.firstName];
            if (event.lastTimeslot !== timeslot - 1) { // This event isn't continued from last
              delete tempEvents[employee.firstName]; // Clear it off of the temp map
              events.push(event);
              event = null;
            }
          }
          if (event === null) {
            // Start a new event
            event = {
              startsAt: moment().startOf('isoWeek')
                .add(dayVal, 'day')
                .add(parseInt(timeslot) / 2, 'hour')
                .toDate(),
              color: {
                primary: '#0049d',
                secondary: '#fffff'
              }
            };
          }
          
          if (this.edit) {
            event.actions = [{
              label: '<i class=\'glyphicon glyphicon-pencil\'></i>',
              onClick: event => {
                this.openEditEventModal(event);
              }
            }];
          }

          event.lastTimeslot = timeslot;
          event.employee = employee;
          event.dow = dayName;
          event.endsAt = moment().startOf('isoWeek')
            .add(dayVal, 'day')
            .add(((parseInt(timeslot) / 2) + 0.5), 'hour')
            .toDate();
          const startTime = event.startsAt.toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'});
          const endTime = event.endsAt.toLocaleTimeString([], {hour: '2-digit', minute: '2-digit'});

          event.title = employee.firstName + ' ' + startTime + '-' + endTime;
          tempEvents[employee.firstName] = event;
        }
      }
      // Add all remaining temp events
      for (const empName of Object.keys(tempEvents)) {
        const event = tempEvents[empName];
        events.push(event);
      }
    }
    console.log(events);
    return events;
  }
    
  resetView() {
    this.calendar.calendarView = 'week';
  }

  translateObject() {
    // weekStart = moment().startOf('isoWeek').toDate();
  }

  /**
   * Opens the modal for editing/adding employee shifts to an existing schedule
   * @param {*} event A shift held by an employee within a schedule
   */
  openEditEventModal(event) {
    const modal = this.$uibModal.open({
      animation: true,
      template: editEventTemplate,
      controller: EditEventCtrl,
      controllerAs: '$ctrl',
      resolve: {
        event: () => event
      }
    });
    modal.result.then(shifts => {
      if (!shifts) {
        return;
      }
      for (const shift of shifts) {
        if (shift.delete) {
          this.deleteShift(shift);
        } else {
          this.addShift(shift);
        }
      }
    });
  }

  addShift(shift) {
    const dayTimeslotObj = this.schedule.days[shift.dow].scheduledTimeslots;
    for (let i = shift.startTimeslot; i < shift.endTimeslot; i++) {
      if (!dayTimeslotObj[i]) {
        dayTimeslotObj[i] = [];
      }
      dayTimeslotObj[i].push(shift.employee);
    }
    this.reloadScheduleDisplay();
  }

  deleteShift(shift) {
    const dayTimeslotObj = this.schedule.days[shift.dow].scheduledTimeslots;
    for (let i = shift.startTimeslot; i < shift.endTimeslot; i++) {
      if (!dayTimeslotObj[i]) {
        continue; // Nothing to delete
      }
      _.remove(dayTimeslotObj[i], employee => employee.employeeId === shift.employee.employeeId);
    }
    this.reloadScheduleDisplay();
  }

  reloadScheduleDisplay() {
    this.events = this.createEventsFromSchedule(this.schedule);
    this.calendar = {
      calendarView: 'week',
      events: this.events,
      viewDate: moment()
    };
  }

  deleteSchedule() {
    this.LoadingService.loading = true;
    this.ScheduleService.deleteSchedule(this.selectedDate)
      .then(() => {
        this.LoadingService.loading = false;
        this.$window.location.reload(true);
      })
      .catch(error => {
        let message;
        if (error.data && error.data.message) {
          message = error.data.message;
        } else {
          message = 'An unknown error occurred';
          console.error(error);
        }
        this.AlertHandler.error(message);
        this.LoadingService.loading = false;
      });
  }
}
