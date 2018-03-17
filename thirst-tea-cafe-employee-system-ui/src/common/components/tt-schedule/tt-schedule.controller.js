import moment from 'moment';

export default class {

  constructor() {
    'ngInject';
    angular.extend(this, {});

    this.calendar = {
      calendarView: 'week',
      events: [
        {
          title: 'My event title', // The title of the event
          startsAt: new Date(2018,2,15,12), // A javascript date object for when the event starts
          endsAt: new Date(2018,2,17,5), // Optional - a javascript date object for when the event ends
          color: { // can also be calendarConfig.colorTypes.warning for shortcuts to the deprecated event types
            primary: '#e3bc08', // the primary event color (should be darker than secondary)
            secondary: '#fdf1ba' // the secondary event color (should be lighter than primary)
          },
          actions: [{ // an array of actions that will be displayed next to the event title
            label: '<i class=\'glyphicon glyphicon-pencil\'></i>', // the label of the action
            cssClass: 'edit-action', // a CSS class that will be added to the action element so you can implement custom styling
            onClick: args => { // the action that occurs when it is clicked. The first argument will be an object containing the parent event
              console.log('Edit event', args.calendarEvent);
            }
          }],
          draggable: true, //Allow an event to be dragged and dropped
          resizable: true, //Allow an event to be resizable
          incrementsBadgeTotal: true, //If set to false then will not count towards the badge total amount on the month and year view
          recursOn: 'year', // If set the event will recur on the given period. Valid values are year or month
          cssClass: 'a-css-class-name', //A CSS class (or more, just separate with spaces) that will be added to the event when it is displayed on each view. Useful for marking an event as selected / active etc
          allDay: false // set to true to display the event as an all day event on the day view
        }
      ],
      viewDate: moment()
    };
  }

}
