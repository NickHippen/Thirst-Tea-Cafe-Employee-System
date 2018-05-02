package com.thirstteacafe.employees.dto;

import java.util.HashMap;
import java.util.List;

/**
 * DTO object to describe employee availability for a week
 * @author nhipp
 *
 */
public class Availability extends HashMap<DayOfWeek, List<DailyAvailability>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7611814907076513873L;

}
