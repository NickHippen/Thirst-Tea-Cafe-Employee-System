package com.thirstteacafe.employees.employee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.thirstteacafe.employees.dto.Availability;
import com.thirstteacafe.employees.dto.DayOfWeek;
import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.Shift;
import com.thirstteacafe.employees.employee.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

	@Autowired
	private EmployeeService employeeService;
	
	private Employee employee;
	
	@Before
	public void before() {
		employee = new Employee();
		for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
			if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
				continue;
			}
			employee.addAvailability(dayOfWeek,
					new ImmutablePair<LocalTime, LocalTime>(LocalTime.of(8, 0), LocalTime.of(17, 0)));
		}
	}
	
	@Test
	public void testEmployeeAvailable() {
		assertTrue(employeeService.checkAvailabilityForShift(employee, new Shift(DayOfWeek.MONDAY, 16, 24))); // 08:00-12:00
		assertTrue(employeeService.checkAvailabilityForShift(employee, new Shift(DayOfWeek.MONDAY, 24, 34))); // 12:00-17:00
		assertTrue(employeeService.checkAvailabilityForShift(employee, new Shift(DayOfWeek.MONDAY, 20, 32))); // 10:00-16:00
		assertTrue(employeeService.checkAvailabilityForShift(employee, new Shift(DayOfWeek.MONDAY, 17, 33))); // 08:30-16:30
	}
	
	@Test
	public void testEmployeeUnavailable() {
		assertFalse(employeeService.checkAvailabilityForShift(employee, new Shift(DayOfWeek.MONDAY, 0, 8)));   // 00:00-04:00
		assertFalse(employeeService.checkAvailabilityForShift(employee, new Shift(DayOfWeek.MONDAY, 6, 24)));  // 03:00-12:00
		assertFalse(employeeService.checkAvailabilityForShift(employee, new Shift(DayOfWeek.MONDAY, 24, 35))); // 12:00-17:30
		assertFalse(employeeService.checkAvailabilityForShift(employee, new Shift(DayOfWeek.MONDAY, 1, 17)));  // 00:30-08:30
		assertFalse(employeeService.checkAvailabilityForShift(employee, new Shift(DayOfWeek.SATURDAY, 12, 13)));  // 12:00-13:00
	}
	
	@Test
	public void testGetAllEmployees() {
		List<Employee> employees = employeeService.getAllEmployees();
		assertNotNull(employees);
	}
	
	@Test
	public void createAndDeleteEmployee() {
		this.createTestEmployee();
		Employee createdEmployee = this.findTestEmployee();
		assertNotNull(createdEmployee);
		employeeService.deleteEmployee(createdEmployee.getEmployeeId());
		assertNull(employeeService.getEmployee(createdEmployee.getEmployeeId()));
	}
	
	@Test
	public void createGetAndDeleteEmployeeAvailability() {
		this.createTestEmployee();
		Employee createdEmployee = this.findTestEmployee();
		
		Availability availToCreate = new Availability();
		availToCreate.put(DayOfWeek.MONDAY, Arrays.asList(new ImmutablePair<LocalTime, LocalTime>(LocalTime.of(0, 0), LocalTime.of(12, 0))));
		employeeService.addAvailability(createdEmployee.getEmployeeId(), availToCreate);
		Availability createdAvail = employeeService.getAvailability(createdEmployee.getEmployeeId());
		assertNotNull(createdAvail);
		assertEquals(1, createdAvail.keySet().size());
		assertEquals(1, createdAvail.get(DayOfWeek.MONDAY).size());
		
		availToCreate = new Availability();
		availToCreate.put(DayOfWeek.MONDAY, Arrays.asList(new ImmutablePair<LocalTime, LocalTime>(LocalTime.of(14, 0), LocalTime.of(18, 0))));
		employeeService.addAvailability(createdEmployee.getEmployeeId(), availToCreate);
		createdAvail = employeeService.getAvailability(createdEmployee.getEmployeeId());
		assertNotNull(createdAvail);
		assertEquals(1, createdAvail.keySet().size());
		assertEquals(2, createdAvail.get(DayOfWeek.MONDAY).size());
		
		availToCreate = new Availability();
		availToCreate.put(DayOfWeek.WEDNESDAY, Arrays.asList(new ImmutablePair<LocalTime, LocalTime>(LocalTime.of(14, 0), LocalTime.of(18, 0))));
		employeeService.addAvailability(createdEmployee.getEmployeeId(), availToCreate);
		createdAvail = employeeService.getAvailability(createdEmployee.getEmployeeId());
		assertNotNull(createdAvail);
		assertEquals(2, createdAvail.keySet().size());
		assertEquals(1, createdAvail.get(DayOfWeek.WEDNESDAY).size());
		
		employeeService.deleteEmployee(createdEmployee.getEmployeeId());
	}
	
	private void createTestEmployee() {
		Employee employeeToCreate = new Employee();
		employeeToCreate.setFirstName("Test");
		employeeToCreate.setLastName("Employee");
		employeeToCreate.setAdmin(true);
		employeeToCreate.setCanLift(true);
		employeeToCreate.setDrinkMaker(true);
		employeeToCreate.setFoodMaker(true);
		employeeToCreate.setMaxHours(60);
		employeeToCreate.setMinHours(20);
		employeeToCreate.setUsername("testuser");
		employeeToCreate.setPassword("testpassword");
		employeeService.createEmployee(employeeToCreate);
	}
	
	private Employee findTestEmployee() {
		List<Employee> employees = employeeService.getAllEmployees();
		Employee createdEmployee = null;
		for (Employee employee : employees) {
			if (employee.getFirstName().equals("Test")
					&& employee.getLastName().equals("Employee")) {
				createdEmployee = employee;
				break;
			}
		}
		return createdEmployee;
	}
	
}
