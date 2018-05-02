package com.thirstteacafe.employees.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DTO object to describe a schedule for a single day
 */
public class DailySchedule {

	private Map<Integer, List<Employee>> scheduledTimeslots = new HashMap<>(); // 0 = 00:00, 1 = 00:30, 2 = 01:00, ...

	public Map<Integer, List<Employee>> getScheduledTimeslots() {
		return scheduledTimeslots;
	}

	public void setScheduledTimeslots(Map<Integer, List<Employee>> scheduledTimeslots) {
		this.scheduledTimeslots = scheduledTimeslots;
	}
	
	/**
	 * Schedules an employee for the specified timeslot
	 * @param timeslot
	 * @param employee
	 */
	public void scheduleEmployee(int timeslot, Employee employee) {
		checkTimeslot(timeslot);
		List<Employee> existing = getScheduledTimeslots().getOrDefault(timeslot, new ArrayList<Employee>());
		existing.add(employee);
		getScheduledTimeslots().put(timeslot, existing);
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
		return getScheduledTimeslots().getOrDefault(timeslot, new ArrayList<Employee>());
	}
	
	/**
	 * Unschedules all employees from a timeslot
	 * @param timeslot
	 */
	public void unscheduleAll(int timeslot) {
		checkTimeslot(timeslot);
		getScheduledTimeslots().remove(timeslot);
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

	@Override
	public String toString() {
		return "DailySchedule [scheduledTimeslots=" + scheduledTimeslots + "]";
	}

}
