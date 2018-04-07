package com.thirstteacafe.employees.employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Arrays;

import org.springframework.jdbc.core.RowMapper;

import com.thirstteacafe.employees.dto.Availability;
import com.thirstteacafe.employees.dto.DayOfWeek;
import com.thirstteacafe.employees.dto.DailyAvailability;
import com.thirstteacafe.employees.timeslot.TimeslotService;

public class AvailabilityMapper implements RowMapper<Availability> {

	private TimeslotService timeslotService;
	
	public AvailabilityMapper(TimeslotService timeslotService) {
		this.timeslotService = timeslotService;
	}
	
	@Override
	public Availability mapRow(ResultSet rs, int arg1) throws SQLException {
		Availability availability = new Availability();
		Time startTime = rs.getTime("avail_start");
		Time endTime = rs.getTime("avail_end");
		DailyAvailability timeRange = new DailyAvailability(timeslotService.convertLocalTime(startTime.toLocalTime()), timeslotService.convertLocalTime(endTime.toLocalTime()));
		timeRange.setAvailabilityId(rs.getLong("avail_id"));
		int dowOffset = rs.getInt("avail_dayofweek");
		availability.put(DayOfWeek.fromOffset(dowOffset), Arrays.asList(timeRange));
		return availability;
	}

}
