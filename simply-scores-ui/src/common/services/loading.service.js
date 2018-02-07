export default class {

  constructor() {
    'ngInject';
    angular.extend(this, {});
  }

  get loading() {
    return this._loading;
  }

  set loading(value) {
    this._loading = value;
  }

}
