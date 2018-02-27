package com.thirstteacafe.employees.schedule;

import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.Shift;

public interface EmployeeService {

	/**
	 * Checks whether or not employee has work availability for shift
	 * @param employee
	 * @param shift
	 * @return whether or not employee has work availability for shift
	 */
	boolean checkAvailabilityForShift(Employee employee, Shift shift);
	
}
