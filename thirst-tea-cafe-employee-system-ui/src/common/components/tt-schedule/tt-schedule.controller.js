import moment from 'moment';
import EditEventCtrl from './edit-event/edit-event.controller';
import editEventTemplate from './edit-event/edit-event.html';

export default class {

  constructor($uibModal, TimeslotService, AlertHandler, ScheduleService, LoadingService, DAY_OF_WEEK) {
    'ngInject';
    angular.extend(this, {$uibModal, TimeslotService, AlertHandler, ScheduleService, LoadingService, DAY_OF_WEEK});
  }

  // $onInit() {
  //   this.dumbySchedule = {
  //     'days': {
  //       'MONDAY': {
  //         'scheduledTimeslots': {
  //           '24': [{'name': 'Nick H'}],
  //           '25': [{'name': 'Nick H'}],
  //           '26': [{'name': 'Nick H'}],
  //           '21': [{'name': 'Nick H'}],
  //           '22': [{'name': 'Nick H'}]
  //         }
  //       },
  //       'TUESDAY': {
  //         'scheduledTimeslots': {
  //           '10': [{'name': 'Nick H'}]
  //         }
  //       }
  //     }
  //   };
  //   this.events = this.createEventsFromSchedule(this.dumbySchedule);
  //   this.calendar = {
  //     calendarView: 'week',
  //     events: this.events,
  //     viewDate: moment()
  //   };
  //   moment.locale('en', {
  //     week: {
  //       dow: 1 // Monday is the first day of the week
  //     }
  //   });
  // }

  $onChanges(changes) {
    console.log('test', changes);
    if (!this.schedule) {
      return;
    }
    this.events = this.createEventsFromSchedule(this.schedule);
    this.calendar = {
      calendarView: 'week',
      events: this.events,
      viewDate: moment()
    };
    moment.locale('en', {
      week: {
        dow: 1 // Monday is the first day of the week
      }
    });
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
          
          event.lastTimeslot = timeslot;
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

  openEditEventModal(event) {
    this.$uibModal.open({
      animation: true,
      template: editEventTemplate,
      controller: EditEventCtrl,
      controllerAs: '$ctrl',
      resolve: {
        event: () => event
      }
    });
  }
}
