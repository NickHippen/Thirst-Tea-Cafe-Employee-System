package com.thirstteacafe.employees.schedule;

import com.thirstteacafe.employees.dto.ScheduleResult;
import com.thirstteacafe.employees.dto.WeeklySchedule;

public interface ScheduleDao {

	WeeklySchedule getSchedule();
	
}
