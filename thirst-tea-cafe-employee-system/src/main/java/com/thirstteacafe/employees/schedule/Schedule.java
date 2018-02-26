package com.thirstteacafe.employees.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schedule {

	private final Map<Integer, List<Employee>> scheduledTimeslots = new HashMap<>(); // 0 = 00:00, 1 = 00:30, 2 = 01:00, ...
	
	/**
	 * Schedules an employee for the specified timeslot
	 * @param timeslot
	 * @param employee
	 */
	public void scheduleEmployee(int timeslot, Employee employee) {
		checkTimeslot(timeslot);
		List<Employee> existing = scheduledTimeslots.getOrDefault(timeslot, new ArrayList<Employee>());
		existing.add(employee);
		scheduledTimeslots.put(timeslot, existing);
	}
	
	/**
	 * Schedules into all slots from timeslotMin to timeslotMax <b>inclusive</b>.
	 * @param timeslotMin
	 * @param timeslotMax
	 * @param employee
	 */
	public void scheduleEmployee(int timeslotMin, int timeslotMax, Employee employee) {
		checkTimeslot(timeslotMin);
		checkTimeslot(timeslotMax);
		for (int timeslot = timeslotMin; timeslot < timeslotMax; timeslot++) {
			scheduleEmployee(timeslot, employee);
		}
	}
	
	/**
	 * Gets all scheduled employees for the timeslot
	 * @param timeslot
	 * @return
	 */
	public List<Employee> getScheduledEmployees(int timeslot) {
		return scheduledTimeslots.getOrDefault(timeslot, new ArrayList<Employee>());
	}
	
	/**
	 * Unschedules all employees from a timeslot
	 * @param timeslot
	 */
	public void unscheduleAll(int timeslot) {
		checkTimeslot(timeslot);
		scheduledTimeslots.remove(timeslot);
	}
	
	
	/**
	 * Checks that timeslot is a valid time
	 * @param timeslot
	 * @throws IndexOutOfBoundsException if timeslot is not within valid times
	 */
	private void checkTimeslot(int timeslot) {
		if (timeslot < 0 || timeslot >= 48) {
			throw new IndexOutOfBoundsException("Timeslot must be between 0 and 47 inclusive");
		}
	}

}
