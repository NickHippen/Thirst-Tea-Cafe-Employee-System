package com.thirstteacafe.employees.schedule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
	
}
