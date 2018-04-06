package com.thirstteacafe.employees.shifts;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

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
            new Object[] { shift.getId(), shift.getDayOfWeek(), shift.getStart(), shift.getEnd(), shift.getNumPeople(), shift.getAdmin() },
            new int[] { Types.INTEGER, Types.INTEGER, Types.TIME, Types.TIME, Types.INTEGER, Types.INTEGER}
        );
    }

    @Override
    public ShiftData getShiftByID(int shiftID) {
    	List<ShiftData> shifts = jdbcTemplate.query(
    		QUERY_SELECT + " FROM shifts M WHERE shift_id = ?",
    		new Object[] { shiftID },
    		new ShiftMapper());
		return shifts.isEmpty() ? null : shifts.get(0);
    }
    
    @Override
    public void deleteShiftByID(int shiftID) {
    	jdbcTemplate.update(
            "DELETE FROM shifts WHERE shift_id = " + shiftID
        );
    }

}
