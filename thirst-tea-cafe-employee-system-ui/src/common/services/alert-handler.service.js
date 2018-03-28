/**
 * A service for handling alerts
 * @module AlertHandler
 */
export default class {

  constructor($timeout) {
    'ngInject';
    angular.extend(this, {$timeout});
  }

  /**
   * Adds an alert to the current page
   * @param {*} alert the alert object to add apply, if no duration is specified the default duration will be 10 seconds
   */
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

  /**
   * Removes an alert by its index
   * @param {Number} index the index of the alert to remove
   */
  removeAlert(index) {
    this.alerts.splice(index, 1);
  }

  /**
   * Creates a danger alert with default settings
   * @param {string} message the message to display on the alert
   */
  error(message) {
    this.addAlert({
      type: 'danger',
      msg: message
    });
  }

  /**
   * The list of currently active alerts
   */
  get alerts() {
    return this._alerts;
  }

  set alerts(alerts) {
    this._alerts = alerts;
  }

}
