package com.thirstteacafe.employees.schedule;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.DailySchedule;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Override
	public void scheduleEmployee(DailySchedule schedule, int timeslot, Employee employee) {
		checkTimeslot(timeslot);
		List<Employee> existing = schedule.getScheduledTimeslots().getOrDefault(timeslot, new ArrayList<Employee>());
		existing.add(employee);
		schedule.getScheduledTimeslots().put(timeslot, existing);
	}
	
	@Override
	public void scheduleEmployee(DailySchedule schedule, int timeslotMin, int timeslotMax, Employee employee) {
		checkTimeslot(timeslotMin);
		checkTimeslot(timeslotMax);
		for (int timeslot = timeslotMin; timeslot < timeslotMax; timeslot++) {
			scheduleEmployee(schedule, timeslot, employee);
		}
	}
	
	@Override
	public List<Employee> getScheduledEmployees(DailySchedule schedule, int timeslot) {
		return schedule.getScheduledTimeslots().getOrDefault(timeslot, new ArrayList<Employee>());
	}
	
	@Override
	public void unscheduleAll(DailySchedule schedule, int timeslot) {
		checkTimeslot(timeslot);
		schedule.getScheduledTimeslots().remove(timeslot);
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
