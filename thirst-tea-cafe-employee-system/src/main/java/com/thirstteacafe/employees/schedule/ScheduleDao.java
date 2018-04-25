package com.thirstteacafe.employees.schedule;

import java.util.Date;

import com.thirstteacafe.employees.dto.WeeklySchedule;
import com.thirstteacafe.employees.exceptions.ScheduleException;

public interface ScheduleDao {
	
	public static final String TABLE = "schedules";

	WeeklySchedule getSchedule(Date date) throws ScheduleException;

	void publishSchedule(Date date, WeeklySchedule schedule) throws ScheduleException;

	void deleteSchedule(Date startOfWeek);

	void updateSchedule(Date startOfWeek, WeeklySchedule schedule) throws ScheduleException;
	
}
