package com.thirstteacafe.employees.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value="/employee/{employeeId}", method=RequestMethod.GET)
	public void getEmployee(@PathVariable Long employeeId) {
		employeeService.getEmployee(employeeId);
	}

}
