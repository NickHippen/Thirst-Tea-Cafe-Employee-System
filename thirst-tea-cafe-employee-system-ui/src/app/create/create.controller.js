export default class {

  constructor($log, CreateService, ScheduleService, LoadingService) {
    'ngInject';
    angular.extend(this, {$log, CreateService, ScheduleService, LoadingService});
  }

  generateSchedule() {
    // Probably a call to backend to generate schedule first here, then the content below goes into the async callback
    this.LoadingService.loading = true;
    this.ScheduleService.generateSchedule()
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
    this.showSchedule = true;
  }

  editExistingSchedule() {
    // Open modal selecting week for schedule or something like that
    // When modal is submitted, do this.showSchedule = true; with this.scheduleObj being the selected schedule
    this.LoadingService.loading = true;
    this.ScheduleService.getSchedule()
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
    this.showSchedule = true;
  }
}
