import moment from 'moment';

export default class {

  constructor(TimeslotService, DAY_OF_WEEK) {
    'ngInject';
    angular.extend(this, {TimeslotService, DAY_OF_WEEK});

    this.shifts = [];
    
    this.shifts.push({
      empName: 'Vincent Nguyen',
      startDate: new Date(2018, 2, 26, 12),
      endDate: new Date(2018, 2, 26, 14),
      empColor: '#00428'
    });
    
    this.events = [];  
    for (let i = 0; i < this.shifts.length; i++) {
      this.events.push({
        title: this.shifts[i].empName, // Set to employee's name
        startsAt: this.shifts[i].startDate, // Set to start/end time of shift
        endsAt: this.shifts[i].endDate,
        color: { // Set this color to employee's saved color
          primary: this.shifts[i].empColor, 
          secondary: '#fffff' 
        },
        actions: [{ // an array of actions that will be displayed next to the event title
          label: '<i class=\'glyphicon glyphicon-pencil\'></i>', // the label of the action
          cssClass: 'edit-action', // a CSS class that will be added to the action element so you can implement custom styling
          onClick: args => { // the action that occurs when it is clicked. The first argument will be an object containing the parent event
            console.log('Edit event', args.calendarEvent);
          }
        }],
        draggable: false, //Allow an event to be dragged and dropped
        resizable: false, //Allow an event to be resizable
        incrementsBadgeTotal: true, //If set to false then will not count towards the badge total amount on the month and year view
        cssClass: 'a-css-class-name', //A CSS class (or more, just separate with spaces) that will be added to the event when it is displayed on each view. Useful for marking an event as selected / active etc
        allDay: false // set to true to display the event as an all day event on the day view
      });
    }  
    this.calendar = {
      calendarView: 'week',
      events: this.events,
      viewDate: moment()
    };
  }
    
  resetView() {
    this.calendar.calendarView = 'week';
  }
}
