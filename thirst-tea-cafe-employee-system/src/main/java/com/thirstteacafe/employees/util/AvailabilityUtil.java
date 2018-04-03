package com.thirstteacafe.employees.util;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import com.thirstteacafe.employees.dto.Availability;
import com.thirstteacafe.employees.dto.DayOfWeek;

@Component
public class AvailabilityUtil {
	
	public Availability consolidate(List<Availability> availabilities) {
		Availability consolidatedAvail = new Availability();
		for (Availability availability : availabilities) {
			for (DayOfWeek dow : availability.keySet()) {
				List<Pair<LocalTime, LocalTime>> dayAvail = consolidatedAvail.getOrDefault(dow, new ArrayList<>());
				dayAvail.addAll(availability.get(dow));
				consolidatedAvail.put(dow, dayAvail);
			}
		}
		return consolidatedAvail;
	}
	
}
