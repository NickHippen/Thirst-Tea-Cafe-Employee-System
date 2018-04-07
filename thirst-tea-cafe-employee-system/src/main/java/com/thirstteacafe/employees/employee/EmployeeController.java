package com.thirstteacafe.employees.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thirstteacafe.employees.dto.Availability;
import com.thirstteacafe.employees.dto.Employee;

@CrossOrigin
@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value="/employee/{employeeId}", method=RequestMethod.GET)
	public Employee getEmployee(@PathVariable Long employeeId) {
		return employeeService.getEmployee(employeeId);
	}
	
	@RequestMapping(value="/employee", method=RequestMethod.POST)
	public void createEmployee(@RequestBody Employee employee) {
		employeeService.createEmployee(employee);
	}
	
	@RequestMapping(value="/employee/{employeeId}", method=RequestMethod.PUT)
	public void updateEmployee(@PathVariable Long employeeId, @RequestBody Employee employee) {
		employeeService.updateEmployee(employeeId, employee);
	}
	
	@RequestMapping(value="/employee/{employeeId}", method=RequestMethod.DELETE)
	public void deleteEmployee(@PathVariable Long employeeId) {
		employeeService.deleteEmployee(employeeId);
	}
	
	@RequestMapping(value="/employee", method=RequestMethod.GET)
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}
	
	@RequestMapping(value="/employee/{employeeId}/availability", method=RequestMethod.POST)
	public Employee addAvailability(@PathVariable Long employeeId, @RequestBody Availability availability) {
		return employeeService.addAvailability(employeeId, availability);
	}
	
	@RequestMapping(value="/employee/{employeeId}/availability/{availabilityId}", method=RequestMethod.DELETE)
	public Employee deleteAvailability(@PathVariable Long employeeId, @PathVariable Long availabilityId) {
		return employeeService.deleteAvailability(employeeId, availabilityId);
	}

}
