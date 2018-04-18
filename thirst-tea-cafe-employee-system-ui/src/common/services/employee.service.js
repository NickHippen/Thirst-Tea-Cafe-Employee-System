export default class {

  constructor($http, REST_CONSTANTS) {
    'ngInject';
    angular.extend(this, {$http, REST_CONSTANTS});
  }

  deleteEmployee(employeeId) {
    return this.$http({
      method: 'DELETE',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/employee/${employeeId}`
    });
  }

  getAllEmployees() {
    return this.$http({
      method: 'GET',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/employee`
    });
  }

  createEmployee(employee) {
    return this.$http({
      method: 'POST',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/employee`,
      data: angular.toJson(employee)
    });
  }

  updateEmployee(employee) {
    return this.$http({
      method: 'PUT',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/employee/${employee.employeeId}`,
      data: angular.toJson(employee)
    });
  }

  addAvailability(employeeId, availability) {
    return this.$http({
      method: 'POST',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/employee/${employeeId}/availability`,
      data: angular.toJson(availability)
    });
  }

  deleteAvailability(employeeId, availabilityId) {
    return this.$http({
      method: 'DELETE',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/employee/${employeeId}/availability/${availabilityId}`
    });
  }

}