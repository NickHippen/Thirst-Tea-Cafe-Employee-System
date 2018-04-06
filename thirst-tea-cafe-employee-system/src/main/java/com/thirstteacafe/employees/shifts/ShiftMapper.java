package com.thirstteacafe.employees.shifts;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class ShiftMapper implements RowMapper<ShiftData> {

	@Override
	public ShiftData mapRow(ResultSet rs, int arg1) throws SQLException {
		ShiftData results = new ShiftData();
        results.setId(rs.getInt("shift_id"));
        results.setDayOfWeek(rs.getInt("shift_dayofweek"));
        results.setStart(rs.getTime("shift_start").toLocalTime());
        results.setEnd(rs.getTime("shift_end").toLocalTime());
        results.setNumPeople(rs.getInt("shift_numpeople"));
        results.setAdmin(rs.getInt("shift_admin"));
        return results;
	}

}
