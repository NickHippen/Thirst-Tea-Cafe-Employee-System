import moment from 'moment';

export default class {

  constructor(TimeslotService, AlertHandler, ScheduleService, LoadingService, DAY_OF_WEEK) {
    'ngInject';
    angular.extend(this, {TimeslotService, AlertHandler, ScheduleService, LoadingService, DAY_OF_WEEK});
  }

  $onInit() {
    // const weeklySchedule = {
    //   'days': {
    //     'MONDAY': {
    //       'scheduledTimeslots': {
    //         '22': [{'name': 'Vincent N'}],
    //         '23': [{'name': 'Vincent N'}],
    //         '24': [{'name': 'Vincent N'}],
    //         '25': [{'name': 'Vincent N'}],
    //         '26': [{'name': 'Vincent N'}],
    //         '27': [{'name': 'Vincent N'}],
    //         '28': [{'name': 'Vincent N'}],
    //         '29': [{'name': 'Vincent N'}],
    //         '30': [{'name': 'Nick H'}],
    //         '31': [{'name': 'Nick H'}],
    //         '32': [{'name': 'Nick H'}],
    //         '33': [{'name': 'Nick H'}],
    //         '34': [{'name': 'Mitch H'}],
    //         '35': [{'name': 'Mitch H'}],
    //         '36': [{'name': 'Mitch H'}],
    //         '37': [{'name': 'Mitch H'}],
    //         '38': [{'name': 'Hayden F'}],
    //         '39': [{'name': 'Hayden F'}],
    //         '40': [{'name': 'Hayden F'}],
    //         '41': [{'name': 'Hayden F'}]
    //       }
    //     },
    //     'TUESDAY': {
    //       'scheduledTimeslots': {
    //         '10': [{'name': 'Nick H'}]
    //       }
    //     }
    //   }
    // };
    // this.events = this.createEventsFromSchedule(weeklySchedule);
    // this.calendar = {
    //   calendarView: 'week',
    //   events: this.events,
    //   viewDate: moment()
    // };
    this.LoadingService.loading = true;
    let weeklySchedule = {};
    this.ScheduleService.getSchedule(this.date)
      .then(response => {
        weeklySchedule = response.data;
        this.events = this.createEventsFromSchedule(weeklySchedule);
        this.calendar = {
          calendarView: 'week',
          events: this.events,
          viewDate: moment()
        };
        this.LoadingService.loading = false;
      })
      .catch(error => {
        let message;
        if (error.data && error.data.message) {
          message = error.data.message;
        } else {
          message = 'An unknown error occurred';
        }
        this.AlertHandler.error(message);
        this.LoadingService.loading = false;
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
}
