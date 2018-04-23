package com.thirstteacafe.employees.shifts;

import java.util.List;
public interface ShiftDao {

	void createShift(ShiftData shift);
	
	ShiftData getShiftByID(int shiftID);

	List<ShiftData> getAllShifts();

	void deleteShiftByID(int shiftID);
	
}
