package com.thirstteacafe.employees.employee;

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

}
