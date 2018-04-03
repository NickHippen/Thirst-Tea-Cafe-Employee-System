package com.thirstteacafe.employees.shifts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class JdbcShiftDao implements ShiftDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

    private String QUERY_SELECT = 
        "SELECT "
        + "M.shift_id, "
        + "M.shift_dayofweek, "
        + "M.shift_start, "
        + "M.shift_end, "
        + "M.shift_numpeople, "
        + "M.shift_admin";

    @Override
    public void createShift(ShiftData shift) {
        jdbcTemplate.update
        (
            "INSERT INTO shifts (shift_id, shift_dayofweek, shift_start, shift_end, shift_numpeople, shift_admin) VALUES (?, ?, ?, ?, ?, ?)",
            new Object[] { shift.getId(), shift.getDayOfWeek(), shift.getStart(), shift.getEnd(), shift.getNumPeople(), shift.isAdmin() },
            new int[] { Types.INTEGER, Types.INTEGER, Types.TIME, Types.TIME, Types.INTEGER, Types.BOOLEAN}
        );
    }

    @Override
    public ShiftData getShiftByID(int shiftID) {
        List<ShiftData> Shifts = jdbcTemplate.query(
			QUERY_SELECT + " FROM shifts M WHERE shift_id = ?",
			new Object[] { shiftID },
			new RowMapper<ShiftData>() {
				@Override
				public ShiftData mapRow(ResultSet rs, int rowNum) throws SQLException {
					ShiftData results = createShiftDataFromResultSet(rs);
					return results;
				}
			}
		);
		return Shifts.isEmpty() ? null : Shifts.get(0);
    }
    
    @Override
    public void deleteShiftByID(int shiftID) {
    	jdbcTemplate.update(
            "DELETE FROM shifts WHERE shift_id = " + shiftID
        );
    }

    public static ShiftData createShiftDataFromResultSet(ResultSet rs) throws SQLException {
        ShiftData results = new ShiftData();
        results.setId(rs.getInt("shift_id"));
        results.setDayOfWeek(rs.getInt("shift_dayofweek"));
        results.setStart(rs.getTime("shift_start").toLocalTime());
        results.setEnd(rs.getTime("shift_end").toLocalTime());
        results.setNumPeople(rs.getInt("shift_numpeople"));
        results.setAdmin(rs.getBoolean("shift_admin"));
        return results;
    }

}
