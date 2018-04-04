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

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiftServiceTest {

	@Autowired
	private ShiftService shiftService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

    private static final int DAYOFWEEK = 0;
    private static final int START     = 1;
    private static final int END       = 2;
    private static final int NUMPEOPLE = 3;
    private static final int ADMIN     = 4;
    
    private static final LocalTime OPEN  = LocalTime.of(11, 00);
    private static final LocalTime MID   = LocalTime.of(17, 30);
    private static final LocalTime CLOSE = LocalTime.of(21, 00);
    
    Object[][] shiftVals = 
    {
        {ShiftData.MONDAY, OPEN, MID, 1, false},
        {ShiftData.MONDAY, MID, CLOSE, 1, false}
    };
    
    private ShiftData createTestShiftData(Object[] vals) {
    	ShiftData sd = new ShiftData();
    	sd.setDayOfWeek((Integer)vals[DAYOFWEEK]);
    	sd.setStart((LocalTime)vals[START]);
    	sd.setEnd((LocalTime)vals[END]);
    	sd.setNumPeople((Integer)vals[NUMPEOPLE]);
    	sd.setAdmin((Boolean)vals[ADMIN]);
    	return sd;
    }
    
    private ShiftData createTestShiftData() {
    	return createTestShiftData(shiftVals[0]);
    }
    
    private ShiftData getShiftWithLargestId() {
        List<ShiftData> Shifts = jdbcTemplate.query(
			"SELECT * FROM shifts ORDER BY shift_id DESC LIMIT 1",
			new ShiftMapper()
		);
		return Shifts.isEmpty() ? null : Shifts.get(0);
    }
        
    /*
     * Create a shift add it to the database, and retrieve it, then delete it.
     */
    @Test 
    public void addGetAndDeleteShift() {
        ShiftData testShift = createTestShiftData();
        shiftService.createShift(testShift);
        int shiftID = getShiftWithLargestId().getId();
     
        ShiftData sd = shiftService.getShiftByID(shiftID);
        assertTrue(sd != null);

        shiftService.deleteShiftByID(sd.getId());
    }
}