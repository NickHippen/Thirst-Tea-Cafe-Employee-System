package com.thirstteacafe.employees.dto;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public class Availability extends HashMap<DayOfWeek, List<Pair<LocalTime, LocalTime>>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7611814907076513873L;

}
