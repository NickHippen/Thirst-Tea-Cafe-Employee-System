package com.thirstteacafe.employees.employee;

import java.util.List;

import com.thirstteacafe.employees.dto.Availability;
import com.thirstteacafe.employees.dto.Employee;

public interface EmployeeDao {

	public static final String TABLE = "employees";
	public static final String AVAILABILITY_TABLE = "availabilities";
	
	Employee getEmployee(Long employeeId);

	List<Employee> getAllEmployees();

	void createEmployee(Employee employee);

	void deleteEmployee(Long employeeId);

	void addAvailability(Long employeeId, Availability availability);

	void deleteAvailability(Long employeeId, Long availabilityId);

	Availability getAvailability(Long employeeId);

	void deleteAllAvailability(Long employeeId);
	
}
