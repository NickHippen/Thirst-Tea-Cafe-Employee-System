package com.thirstteacafe.employees.schedule;

import java.util.List;

import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.Shift;
import com.thirstteacafe.employees.dto.WeeklySchedule;
import com.thirstteacafe.employees.exceptions.ScheduleException;

public interface ScheduleGenerator {

	/**
	 * Creates a schedule with no regards for a previous schedule
	 * @param employees the employees to use for schedule generation
	 * @param shifts the shifts to use for schedule generation
	 * @return the generated schedule
	 * @throws ScheduleException if there was an issue generating the schedule
	 */
	WeeklySchedule scheduleEmployees(List<Employee> employees, List<Shift> shifts) throws ScheduleException;
	
	/**
	 * Creates a schedule with regards to a previous schedule
	 * @param employees the employees to use for schedule generation
	 * @param shifts the shifts to use for schedule generation
	 * @param previousSchedule the previous schedule to consider when generating the schedule
	 * @return the generated schedule
	 * @throws ScheduleException if there was an issue generating the schedule
	 */
	WeeklySchedule scheduleEmployees(List<Employee> employees, List<Shift> shifts, WeeklySchedule previousSchedule) throws ScheduleException;
	
}
