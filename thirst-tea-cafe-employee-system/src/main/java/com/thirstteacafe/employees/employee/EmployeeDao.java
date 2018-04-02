package com.thirstteacafe.employees.employee;

import java.util.List;

import com.thirstteacafe.employees.dto.Employee;

public interface EmployeeDao {

	public static final String TABLE = "employees";
	
	Employee getEmployee(Long employeeId);

	List<Employee> getAllEmployees();
	
}
