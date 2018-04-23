/**
 * A service for handling employees
 * @module EmployeeService
 */
export default class {

  constructor($http, REST_CONSTANTS) {
    'ngInject';
    angular.extend(this, {$http, REST_CONSTANTS});
  }

  /**
   * Performs an ajax call to delete an employee
   * @param {Number} employeeId 
   * @returns a promise to be resolved with the request response
   */
  deleteEmployee(employeeId) {
    return this.$http({
      method: 'DELETE',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/employee/${employeeId}`
    });
  }

  /**
   * Perfroms an ajax call to get all employees
   * @returns a promise to be resolved with the request response
   */
  getAllEmployees() {
    return this.$http({
      method: 'GET',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/employee`
    });
  }

  /**
   * Performs an ajax call to create an employee
   * @param {*} employee 
   * @returns a promise to be resolved with the request response
   */
  createEmployee(employee) {
    return this.$http({
      method: 'POST',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/employee`,
      data: angular.toJson(employee)
    });
  }

  /**
   * Performs an ajax call to update an employee
   * @param {*} employee 
   * @returns a promise to be resolved with the request response
   */
  updateEmployee(employee) {
    return this.$http({
      method: 'PUT',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/employee/${employee.employeeId}`,
      data: angular.toJson(employee)
    });
  }

  /**
   * Performs an ajax call to add availability to an employee
   * @param {Number} employeeId 
   * @param {*} availability 
   * @returns a promise to be resolved with the request response
   */
  addAvailability(employeeId, availability) {
    return this.$http({
      method: 'POST',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/employee/${employeeId}/availability`,
      data: angular.toJson(availability)
    });
  }

  /**
   * Perfroms an ajax call to delete an availability on an employee
   * @param {Number} employeeId 
   * @param {*} availabilityId 
   * @returns a promise to be resolved with the request response
   */
  deleteAvailability(employeeId, availabilityId) {
    return this.$http({
      method: 'DELETE',
      url: `${this.REST_CONSTANTS.BASE_URL}:${this.REST_CONSTANTS.PORT}/employee/${employeeId}/availability/${availabilityId}`
    });
  }

}
