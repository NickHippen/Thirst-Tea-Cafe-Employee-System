package com.thirstteacafe.employees.shifts;

import com.thirstteacafe.employees.dto.Shift;
import com.thirstteacafe.employees.dto.DayOfWeek;
import com.thirstteacafe.employees.timeslot.TimeslotService;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Component;

@Component
public class JdbcShiftDao implements ShiftDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private ShiftMapper shiftMapper;
    
    @Autowired
    private TimeslotService timeslotService;

    private String QUERY_SELECT = 
        "SELECT "
        + "M.shift_id, "
        + "M.shift_dayofweek, "
        + "M.shift_start, "
        + "M.shift_end, "
        + "M.shift_numpeople, "
        + "M.shift_admin";

    @Override
    public void createShift(Shift shift) {
        jdbcTemplate.update
        (
            "INSERT INTO shifts (shift_dayofweek, shift_start, shift_end, shift_numpeople, shift_admin) VALUES (?, ?, ?, ?, ?)",
            new Object[] {
                shift.getDayOfWeek().getOffset(),
                timeslotService.convertTimeslot(shift.getStartTimeslot()), 
                timeslotService.convertTimeslot(shift.getEndTimeslot()), 
                shift.getNumEmployees(), 
                shift.getNumAdmins() 
            },
            new int[] { Types.INTEGER, Types.TIME, Types.TIME, Types.INTEGER, Types.INTEGER}
        );
    }

    @Override
    public Shift getShiftByID(int shiftID) {
    	List<Shift> shifts = jdbcTemplate.query(
    		QUERY_SELECT + " FROM shifts M WHERE shift_id = ?",
    		new Object[] { shiftID },
    		shiftMapper);
		return shifts.isEmpty() ? null : shifts.get(0);
    }

    @Override
    public List<Shift> getAllShifts() {
    	List<Shift> shifts = jdbcTemplate.query(
    		QUERY_SELECT + " FROM shifts M",
    		shiftMapper);
		return shifts;
    }
    
    @Override
    public void deleteShiftByID(int shiftID) {
    	jdbcTemplate.update(
            "DELETE FROM shifts WHERE shift_id = " + shiftID
        );
    }

}
