package com.thirstteacafe.employees.schedule;

import static org.junit.Assert.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.ScheduleResult;
import com.thirstteacafe.employees.dto.Shift;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleServiceTest {

	@Autowired
	private ScheduleService scheduleService;
	
	private List<Employee> employees;
	private List<Shift> shifts;
	
	@Before
	public void before() {
		Employee emp1 = new Employee("Nick", true, true, true, true);
		Arrays.asList(DayOfWeek.values())
			.forEach((dow) -> emp1.addAvailability(dow, new ImmutablePair<LocalTime, LocalTime>(LocalTime.of(0, 0), LocalTime.of(12, 0))));
		Employee emp2 = new Employee("Hayden", true, true, true, true);
		Arrays.asList(DayOfWeek.values())
			.forEach((dow) -> emp2.addAvailability(dow, new ImmutablePair<LocalTime, LocalTime>(LocalTime.of(12, 0), LocalTime.of(23, 59, 59))));
		employees = Arrays.asList(emp1, emp2);
		
		shifts = new ArrayList<Shift>();
		Arrays.asList(DayOfWeek.values()).forEach((dow) -> {
			shifts.add(new Shift(dow, 2, 8));
			shifts.add(new Shift(dow, 26, 32));
		});
	}
	
	@Test
	public void testSchedule() {
		ScheduleResult result = scheduleService.scheduleEmployees(employees, shifts);
		Arrays.asList(result.getSchedule()).forEach(arr -> System.out.println(Arrays.toString(arr)));
		assertTrue(result.isFeasible());
	}
	
}
