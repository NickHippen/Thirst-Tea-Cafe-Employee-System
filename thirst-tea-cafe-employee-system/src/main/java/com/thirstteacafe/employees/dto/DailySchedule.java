package com.thirstteacafe.employees.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DailySchedule {

	private Map<Integer, List<Employee>> scheduledTimeslots = new HashMap<>(); // 0 = 00:00, 1 = 00:30, 2 = 01:00, ...

	public Map<Integer, List<Employee>> getScheduledTimeslots() {
		return scheduledTimeslots;
	}

	public void setScheduledTimeslots(Map<Integer, List<Employee>> scheduledTimeslots) {
		this.scheduledTimeslots = scheduledTimeslots;
	}

}
