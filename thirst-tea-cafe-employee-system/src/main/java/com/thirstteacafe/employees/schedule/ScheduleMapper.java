package com.thirstteacafe.employees.schedule;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thirstteacafe.employees.dto.WeeklySchedule;

public class ScheduleMapper implements RowMapper<WeeklySchedule> {

	@Override
	public WeeklySchedule mapRow(ResultSet rs, int rowNum) throws SQLException {
		ObjectMapper mapper = new ObjectMapper();
		WeeklySchedule results = new WeeklySchedule();
		try {
			results = mapper.readValue(rs.getString("schedule_object"), WeeklySchedule.class);
		} catch (IOException e) {
			throw new SQLException(e);
		}
		return results;
	}

}
