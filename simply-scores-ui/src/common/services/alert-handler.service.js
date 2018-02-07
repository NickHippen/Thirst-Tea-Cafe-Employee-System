export default class {

  constructor($timeout) {
    'ngInject';
    angular.extend(this, {$timeout});
  }

  addAlert(alert) {
    if (angular.isUndefined(this.alerts)) {
      this.alerts = [];
    }
    if (angular.isUndefined(this.alerts.duration)) {
      this.alerts.duration = 10000; // 10 seconds
    }
    this.alerts.push(alert);
    this.$timeout(() => {
      const index = this.alerts.indexOf(this.alerts);
      this.removeAlert(index);
    }, this.alerts.duration);
  }

  removeAlert(index) {
    this.alerts.splice(index, 1);
  }

  error(message) {
    this.addAlert({
      type: 'danger',
      msg: message
    });
  }

  get alerts() {
    return this._alerts;
  }

  set alerts(alerts) {
    this._alerts = alerts;
  }

}
