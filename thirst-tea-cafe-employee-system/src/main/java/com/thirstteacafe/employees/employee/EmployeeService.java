package com.thirstteacafe.employees.employee;

import java.util.List;

import com.thirstteacafe.employees.dto.Availability;
import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.Shift;

public interface EmployeeService {

	/**
	 * Gets an employee by their employee id
	 * @param employeeId the id to get the employee by
	 * @return the employee matching the id, or null if not found
	 */
	Employee getEmployee(Long employeeId);
	
	/**
	 * Checks whether or not employee has work availability for shift
	 * @param employee
	 * @param shift
	 * @return whether or not employee has work availability for shift
	 */
	boolean checkAvailabilityForShift(Employee employee, Shift shift);

	/**
	 * Gets all employees
	 * @return a list of all employees
	 */
	List<Employee> getAllEmployees();

	/**
	 * Creates an employee
	 * @param employee the employee data to use for creation
	 */
	void createEmployee(Employee employee);

	/**
	 * Deletes an employee
	 * @param employeeId the id of the employee to delete
	 */
	void deleteEmployee(Long employeeId);

	/**
	 * Adds availability to an employee
	 * @param employeeId the id of the employee being modified
	 * @param availability the availability to add to the employee
	 * @return the modified employee
	 */
	Employee addAvailability(Long employeeId, Availability availability);

	/**
	 * Deletes availability from an employee
	 * @param employeeId the id of the employee being modified
	 * @param availabilityId the id of the availability to delete
	 * @return the modified employee
	 */
	Employee deleteAvailability(Long employeeId, Long availabilityId);

	/**
	 * Gets the full availability of an employee
	 * @param employeeId the id of the employee
	 * @return the availability of the employee
	 */
	Availability getAvailability(Long employeeId);

	/**
	 * Deletes all availabilities of an employee
	 * @param employeeId the id of the employee being modified
	 */
	void deleteAllAvailability(Long employeeId);

	/**
	 * Updates an employee
	 * @param employeeId the id of the employee being modified
	 * @param employee the new properties of the employee
	 */
	void updateEmployee(Long employeeId, Employee employee);

}
