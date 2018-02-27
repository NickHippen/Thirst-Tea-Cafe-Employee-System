package com.thirstteacafe.employees.dto;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

public class WeeklySchedule {

	private Map<DayOfWeek, DailySchedule> days = new HashMap<>();

	public Map<DayOfWeek, DailySchedule> getDays() {
		return days;
	}

	public void setDays(Map<DayOfWeek, DailySchedule> days) {
		this.days = days;
	}

}
