import moment from 'moment';

export default class {

  constructor(TimeslotService, DAY_OF_WEEK) {
    'ngInject';
    angular.extend(this, {TimeslotService, DAY_OF_WEEK});

    // BUILD EXAMPLE OBJ
    this.employeesTueNoon = [];
    this.employeesTueNoon.push({
      empName: 'Vincent Nguyen',
      empColor: '#00428'
    });
    this.employeesTueNoon.push({
      empName: 'Nick Hippen',
      empColor: '#11252'
    });

    this.employeesWed3 = [];
    this.employeesWed3.push({
      empName: 'Nick Hippen',
      empColor: '#11252'
    });
    this.employeesWed3.push({
      empName: 'Mitchell Huston',
      empColor: '#11452'
    });

    this.dailySchedule = [];
    this.dailySchedule2 = [];
    this.weeklySchedule = [];

    this.dailySchedule.push({time: 25, employees: this.employeesTueNoon});
    this.dailySchedule2.push({time: 30, employees: this.employeesWed3});

    this.weeklySchedule.push({day: 1, dailySchedule: this.dailySchedule});
    this.weeklySchedule.push({day: 2, dailySchedule: this.dailySchedule2});
    // BUILD EXAMPLE OBJ DONE

    this.scheduledEmployees = [];
    for (let i = 0; i < this.weeklySchedule.length; i++) {
      for (let j = 0; j < this.weeklySchedule[i].dailySchedule.length; j++) {
        for (let k = 0; k < this.weeklySchedule[i].dailySchedule[j].employees.length; k++) {
          this.scheduledEmployees.push({
            empName: this.weeklySchedule[i].dailySchedule[j].employees[k].empName,
            startDate: moment().startOf('isoWeek')
              .add(this.weeklySchedule[i].day, 'day')
              .add(this.weeklySchedule[i].dailySchedule[j].time / 2, 'hour')
              .toDate(),
            endDate: moment().startOf('isoWeek')
            .add(this.weeklySchedule[i].day, 'day')
            .add((this.weeklySchedule[i].dailySchedule[j].time / 2) + 0.5, 'hour')
            .toDate(),
            empColor: this.weeklySchedule[i].dailySchedule[j].employees[k].empColor
          });
        }
      }
    }

    this.events = [];  
    for (let i = 0; i < this.scheduledEmployees.length; i++) {
      this.events.push({
        title: this.scheduledEmployees[i].empName, // Set to employee's name
        startsAt: this.scheduledEmployees[i].startDate, // Set to start/end time of shift
        endsAt: this.scheduledEmployees[i].endDate,
        color: { // Set this color to employee's saved color
          primary: this.scheduledEmployees[i].empColor, 
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

  translateObject() {
    // weekStart = moment().startOf('isoWeek').toDate();
  }
}
