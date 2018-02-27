package com.thirstteacafe.employees.schedule;

import java.time.LocalTime;

public interface TimeslotService {

	/**
	 * Converts LocalTime into timeslot
	 * @param time
	 * @return the converted LocalTime as a timeslot
	 */
	int convertLocalTime(LocalTime time);

}
