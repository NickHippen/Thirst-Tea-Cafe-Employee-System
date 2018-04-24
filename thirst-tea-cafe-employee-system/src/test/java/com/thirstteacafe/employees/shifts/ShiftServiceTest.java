package com.thirstteacafe.employees.shifts;

import org.junit.Test;

import java.util.List;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import static org.junit.Assert.assertTrue;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.thirstteacafe.employees.timeslot.TimeslotService;
import com.thirstteacafe.employees.dto.DayOfWeek;
import com.thirstteacafe.employees.dto.Shift;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiftServiceTest {

	@Autowired
	private ShiftService shiftService;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private TimeslotService timeslotService;
    
    @Autowired
    private ShiftMapper shiftMapper;

    private static final int DAYOFWEEK = 0;
    private static final int START     = 1;
    private static final int END       = 2;
    private static final int NUMPEOPLE = 3;
    private static final int ADMIN     = 4;
    
    private static final LocalTime OPEN  = LocalTime.of(11, 30);
    private static final LocalTime MID   = LocalTime.of(17, 45);
    private static final LocalTime CLOSE = LocalTime.of(21, 30);
    
    Object[][] shiftVals = 
    {
        {DayOfWeek.MONDAY, OPEN, MID, 1, 0},
        {DayOfWeek.MONDAY, MID, CLOSE, 1, 0}
    };
    
    private Shift createTestShift(Object[] vals) {
    	Shift sd = new Shift();
    	sd.setDayOfWeek((DayOfWeek)vals[DAYOFWEEK]);
    	sd.setStartTimeslot(timeslotService.convertLocalTime((LocalTime)vals[START]));
    	sd.setEndTimeslot(timeslotService.convertLocalTime((LocalTime)vals[END]));
    	sd.setNumEmployees((Integer)vals[NUMPEOPLE]);
    	sd.setNumAdmins((Integer)vals[ADMIN]);
    	return sd;
    }
    
    private Shift createTestShift() {
    	return createTestShift(shiftVals[0]);
    }
    
    private Shift getShiftWithLargestId() {
        List<Shift> Shifts = jdbcTemplate.query(
			"SELECT * FROM shifts ORDER BY shift_id DESC LIMIT 1",
			shiftMapper
		);
		return Shifts.isEmpty() ? null : Shifts.get(0);
    }
        
    /*
     * Create a shift add it to the database, and retrieve it, then delete it.
     */
    @Test 
    public void addGetAndDeleteShift() {
        Shift testShift = createTestShift();
        shiftService.createShift(testShift);
        int shiftID = getShiftWithLargestId().getId();
     
        Shift sd = shiftService.getShiftByID(shiftID);
        assertTrue(sd != null);

        shiftService.deleteShiftByID(sd.getId());
    }
    
    @Test
    public void getAllShifts() {
    	List<Shift> shifts = shiftService.getAllShifts();
    	//for (Shift s : shifts) {
    	//	System.out.println(s);
    	//}
    	assert(true);
    }
}