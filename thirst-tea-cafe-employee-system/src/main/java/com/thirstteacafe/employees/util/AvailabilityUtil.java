package com.thirstteacafe.employees.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.thirstteacafe.employees.dto.Availability;
import com.thirstteacafe.employees.dto.DayOfWeek;
import com.thirstteacafe.employees.dto.DailyAvailability;

@Component
public class AvailabilityUtil {
	
	/**
	 * Merges all availabilities into one availability object
	 * @param availabilities the availabilities to merge
	 * @return a new availability object composed of all availabilities merged
	 */
	public Availability consolidate(List<Availability> availabilities) {
		Availability consolidatedAvail = new Availability();
		for (Availability availability : availabilities) {
			for (DayOfWeek dow : availability.keySet()) {
				List<DailyAvailability> dayAvail = consolidatedAvail.getOrDefault(dow, new ArrayList<>());
				dayAvail.addAll(availability.get(dow));
				consolidatedAvail.put(dow, dayAvail);
			}
		}
		return consolidatedAvail;
	}
	
}
