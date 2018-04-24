package com.thirstteacafe.employees.shifts;

import java.util.List;
import com.thirstteacafe.employees.dto.Shift;

public interface ShiftDao {

	void createShift(Shift shift);
	
	Shift getShiftByID(int shiftID);

	List<Shift> getAllShifts();

	void deleteShiftByID(int shiftID);
	
}