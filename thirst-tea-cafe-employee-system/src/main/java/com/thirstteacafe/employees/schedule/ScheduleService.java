package com.thirstteacafe.employees.schedule;

import java.util.List;

import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.DailySchedule;

public interface ScheduleService {

	/**
	 * Schedules an employee for the specified timeslot
	 * @param schedule
	 * @param timeslot
	 * @param employee
	 */
	void scheduleEmployee(DailySchedule schedule, int timeslot, Employee employee);

	/**
	 * Schedules into all slots from timeslotMin to timeslotMax <b>inclusive</b>.
	 * @param schedule
	 * @param timeslotMin
	 * @param timeslotMax
	 * @param employee
	 */
	void scheduleEmployee(DailySchedule schedule, int timeslotMin, int timeslotMax, Employee employee);

	/**
	 * Gets all scheduled employees for the timeslot
	 * @param schedule
	 * @param timeslot
	 * @return
	 */
	List<Employee> getScheduledEmployees(DailySchedule schedule, int timeslot);

	/**
	 * Unschedules all employees from a timeslot
	 * @param schedule
	 * @param timeslot
	 */
	void unscheduleAll(DailySchedule schedule, int timeslot);
	
}
