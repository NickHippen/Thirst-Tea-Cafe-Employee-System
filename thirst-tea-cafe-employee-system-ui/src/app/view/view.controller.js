export default class {

  constructor($log, ViewService, LoadingService, ScheduleService, AlertHandler) {
    'ngInject';
    angular.extend(this, {$log, ViewService, LoadingService, ScheduleService, AlertHandler});

    this.LoadingService.loading = true;
    this.ScheduleService.getSchedule(this.date)
      .then(response => {
        this.schedule = response.data;
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

}
