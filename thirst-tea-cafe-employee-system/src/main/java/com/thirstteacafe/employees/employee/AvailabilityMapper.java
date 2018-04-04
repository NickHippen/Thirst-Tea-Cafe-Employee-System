package com.thirstteacafe.employees.employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Arrays;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.jdbc.core.RowMapper;

import com.thirstteacafe.employees.dto.Availability;
import com.thirstteacafe.employees.dto.DayOfWeek;

public class AvailabilityMapper implements RowMapper<Availability> {

	@Override
	public Availability mapRow(ResultSet rs, int arg1) throws SQLException {
		Availability availability = new Availability();
		Time startTime = rs.getTime("avail_start");
		Time endTime = rs.getTime("avail_end");
		Pair<LocalTime, LocalTime> timeRange = new ImmutablePair<>(startTime.toLocalTime(), endTime.toLocalTime());
		int dowOffset = rs.getInt("avail_dayofweek");
		availability.put(DayOfWeek.fromOffset(dowOffset), Arrays.asList(timeRange));
		return availability;
	}

}
