import moment from 'moment';

export default class {

  constructor(TimeslotService, DAY_OF_WEEK) {
    'ngInject';
    angular.extend(this, {TimeslotService, DAY_OF_WEEK});

    const weeklySchedule = {
      'days': {
        'MONDAY': {
          'scheduledTimeslots': {
            '24': [{'name': 'Nick H'}],
            '25': [{'name': 'Nick H'}],
            '26': [{'name': 'Nick H'}],
            '21': [{'name': 'Nick H'}],
            '22': [{'name': 'Nick H'}]
          }
        },
        'TUESDAY': {
          'scheduledTimeslots': {
            '10': [{'name': 'Nick H'}]
          }
        }
      }
    };
    this.events = this.createEventsFromSchedule(weeklySchedule);
  
    this.calendar = {
      calendarView: 'week',
      events: this.events,
      viewDate: moment()
    };
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
          if (employee.name in tempEvents) {
            event = tempEvents[employee.name];
            if (event.lastTimeslot !== timeslot - 1) { // This event isn't continued from last
              delete tempEvents[employee.name]; // Clear it off of the temp map
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

          event.title = employee.name + ' ' + startTime + '-' + endTime;
          tempEvents[employee.name] = event;
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
}
