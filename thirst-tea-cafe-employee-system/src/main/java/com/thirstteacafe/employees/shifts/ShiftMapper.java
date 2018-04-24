package com.thirstteacafe.employees.shifts;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.thirstteacafe.employees.dto.DayOfWeek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.thirstteacafe.employees.timeslot.TimeslotService;
import com.thirstteacafe.employees.dto.Shift;

@Component
public class ShiftMapper implements RowMapper<Shift> {

	@Autowired
	private TimeslotService timeslotService;
	
	@Override
	public Shift mapRow(ResultSet rs, int arg1) throws SQLException {
        Shift results = new Shift();
        results.setId(rs.getInt("shift_id"));
        results.setDayOfWeek(DayOfWeek.fromOffset(rs.getInt("shift_dayofweek")));
        results.setStartTimeslot(timeslotService.convertLocalTime(rs.getTime("shift_start").toLocalTime()));
        results.setEndTimeslot(timeslotService.convertLocalTime(rs.getTime("shift_end").toLocalTime()));
        results.setNumEmployees(rs.getInt("shift_numpeople"));
        results.setNumAdmins(rs.getInt("shift_admin"));
        return results;
	}

}
