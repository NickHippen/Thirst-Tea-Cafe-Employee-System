package com.thirstteacafe.employees.schedule;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thirstteacafe.employees.dto.WeeklySchedule;
import com.thirstteacafe.employees.exceptions.ScheduleException;

@Component
public class JdbcScheduleDao implements ScheduleDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public WeeklySchedule getSchedule(Date date) throws ScheduleException {
		List<WeeklySchedule> schedules = jdbcTemplate.query(
				String.format("SELECT S.schedule_object FROM %s S"
						+ " WHERE S.schedule_date = ?",
						TABLE),
				new Object[] { date },
				new ScheduleMapper());
		if (schedules.isEmpty()) {
			throw new ScheduleException("No schedule for published for this week!");
		}
		return schedules.get(0);
	}

	@Override
	public void publishSchedule(Date date, WeeklySchedule schedule) throws ScheduleException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonSchedule;
		try {
			jsonSchedule = mapper.writeValueAsString(schedule);
		} catch (JsonProcessingException e) {
			throw new ScheduleException("Unable to write schedule to JSON", e);
		}
		jdbcTemplate.update(String.format(
			"INSERT INTO %s"
				+ " (schedule_date, schedule_object)"
				+ " VALUES (?, ?)",
				TABLE),
			new Object[] { date, jsonSchedule });
	}

	@Override
	public void deleteSchedule(Date date) {
		jdbcTemplate.update(String.format(
				"DELETE FROM %s"
				+ " WHERE schedule_date = ?",
				TABLE),
			new Object[] { date });
	}

	@Override
	public void updateSchedule(Date date, WeeklySchedule schedule) throws ScheduleException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonSchedule;
		try {
			jsonSchedule = mapper.writeValueAsString(schedule);
		} catch (JsonProcessingException e) {
			throw new ScheduleException("Unable to write schedule to JSON", e);
		}
		jdbcTemplate.update(String.format(
				"UPDATE %s"
				+ " SET schedule_object=?"
				+ " WHERE schedule_date = ?",
				TABLE),
			new Object[] { jsonSchedule, date });
	}
	
}
