package com.thirstteacafe.employees.schedule;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.thirstteacafe.employees.dto.WeeklySchedule;

@Component
public class JdbcScheduleDao implements ScheduleDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public WeeklySchedule getSchedule(Date date) {
		List<WeeklySchedule> schedules = jdbcTemplate.query(
				String.format("SELECT S.schedule_object FROM %s S",
						TABLE),
				new ScheduleMapper());
		if (schedules.isEmpty()) {
			return null;
		}
		return schedules.get(0);
	}

}
