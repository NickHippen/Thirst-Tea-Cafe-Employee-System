export default routesConfig;

import moment from 'moment';

function routesConfig(calendarConfig) {
  'ngInject';
  calendarConfig.dateFormatter = 'moment';

  moment.locale('en', {
    week: {
      dow: 1 // Monday
    }
  });
}
