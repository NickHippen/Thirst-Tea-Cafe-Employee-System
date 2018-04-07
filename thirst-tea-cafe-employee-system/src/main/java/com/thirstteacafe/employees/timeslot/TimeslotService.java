package com.thirstteacafe.employees.timeslot;

import java.time.LocalTime;

public interface TimeslotService {

	/**
	 * Converts LocalTime into timeslot
	 * @param time the LocalTime to convert
	 * @return the converted LocalTime as a timeslot
	 */
	int convertLocalTime(LocalTime time);

	/**
	 * Converts timeslot into LocalTime
	 * @param timeslot the timeslot to convert
	 * @return the converted timeslot as a LocalTime
	 */
	LocalTime convertTimeslot(int timeslot);

}
