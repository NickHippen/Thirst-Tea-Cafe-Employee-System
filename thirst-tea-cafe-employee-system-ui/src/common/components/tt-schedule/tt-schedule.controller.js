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
            '26': [{'name': 'Nick H'}]
            // '21': [{'name': 'Nick H'}],
            // '22': [{'name': 'Nick H'}]
          }
        }
        // 'TUESDAY': {
        //   'scheduledTimeslots': {
        //     '10': [{'name': 'Nick H'}]
        //   }
        // }
      }
    };
    this.events = this.createEventsFromSchedule(weeklySchedule);
  
    // for (let i = 0; i < this.scheduledEmployees.length; i++) {
    //   this.events.push({
    //     title: this.scheduledEmployees[i].empName, // Set to employee's name
    //     startsAt: this.scheduledEmployees[i].startDate, // Set to start/end time of shift
    //     endsAt: this.scheduledEmployees[i].endDate,
    //     color: { // Set this color to employee's saved color
    //       primary: this.scheduledEmployees[i].empColor, 
    //       secondary: '#fffff' 
    //     },
    //     actions: [{ // an array of actions that will be displayed next to the event title
    //       label: '<i class=\'glyphicon glyphicon-pencil\'></i>', // the label of the action
    //       cssClass: 'edit-action', // a CSS class that will be added to the action element so you can implement custom styling
    //       onClick: args => { // the action that occurs when it is clicked. The first argument will be an object containing the parent event
    //         console.log('Edit event', args.calendarEvent);
    //       }
    //     }],
    //     draggable: false, //Allow an event to be dragged and dropped
    //     resizable: false, //Allow an event to be resizable
    //     incrementsBadgeTotal: true, //If set to false then will not count towards the badge total amount on the month and year view
    //     cssClass: 'a-css-class-name', //A CSS class (or more, just separate with spaces) that will be added to the event when it is displayed on each view. Useful for marking an event as selected / active etc
    //     allDay: false // set to true to display the event as an all day event on the day view
    //   });
    // }  
    this.calendar = {
      calendarView: 'week',
      events: this.events,
      viewDate: moment()
    };
  }

  createEventsFromSchedule(weeklySchedule) {
    const events = [];

    for (const dayName of Object.keys(weeklySchedule.days)) {
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
              title: employee.name,
              startsAt: moment().startOf('isoWeek')
                .add(parseInt(timeslot) / 2, 'hour')
                .toDate(),
                // this.TimeslotService.convertTimeslotToDate(timeslot),
              color: {
                primary: '#0049d',
                secondary: '#fffff'
              }
            };
          }
          event.lastTimeslot = timeslot;
          event.endsAt = moment().startOf('isoWeek')
            .add(((parseInt(timeslot) / 2) + 0.5), 'hour')
            .toDate();
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
