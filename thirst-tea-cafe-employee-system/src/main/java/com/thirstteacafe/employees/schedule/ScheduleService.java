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
	 * @throws ScheduleException 
	 */
	WeeklySchedule getSchedule(Date date) throws ScheduleException;

	/**
	 * Generates a new schedule for the week of the given date
	 * @param date
	 * @return the generated schedule
	 * @throws ScheduleException if there was an issue generating the schedule
	 */
	WeeklySchedule generateSchedule(Date date) throws ScheduleException;

	/**
	 * Publishes a schedule for the week of the given date
	 * @param date
	 * @param schedule the schedule to be published
	 * @throws ScheduleException 
	 */
	void publishSchedule(Date date, WeeklySchedule schedule) throws ScheduleException;

	/**
	 * Deletes the schedule published for the week of the given date
	 * @param date
	 * @throws ScheduleException
	 */
	void deleteSchedule(Date date) throws ScheduleException;

	/**
	 * Updates & publishes a schedule for the week of the given date
	 * @param date
	 * @param schedule
	 * @throws if there was no schedule for the week
	 */
	void updateSchedule(Date date, WeeklySchedule schedule) throws ScheduleException;

}
