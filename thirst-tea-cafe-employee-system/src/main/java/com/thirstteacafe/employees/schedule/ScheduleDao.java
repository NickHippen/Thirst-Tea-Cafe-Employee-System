package com.thirstteacafe.employees.schedule;

import java.util.Date;

import com.thirstteacafe.employees.dto.WeeklySchedule;

public interface ScheduleDao {
	
	public static final String TABLE = "schedules";

	WeeklySchedule getSchedule(Date date);
	
}
