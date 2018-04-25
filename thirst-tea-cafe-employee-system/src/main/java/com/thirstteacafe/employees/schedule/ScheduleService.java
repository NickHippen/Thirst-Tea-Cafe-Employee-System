package com.thirstteacafe.employees.schedule;

import java.util.Date;
import java.util.List;

import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.ScheduleResult;
import com.thirstteacafe.employees.dto.Shift;
import com.thirstteacafe.employees.dto.WeeklySchedule;
import com.thirstteacafe.employees.exceptions.ScheduleException;

public interface ScheduleService {

	/**
	 * Gets a schedule for the week of the given date
	 * @param date
	 * @return the corresponding schedule stored in the database
	 */
	WeeklySchedule getSchedule(Date date);

	/**
	 * Generates a new schedule for the week of the given date
	 * @param date
	 * @return the generated schedule
	 * @throws ScheduleException if there was an issue generating the schedule
	 */
	WeeklySchedule generateSchedule(Date date) throws ScheduleException;

}
