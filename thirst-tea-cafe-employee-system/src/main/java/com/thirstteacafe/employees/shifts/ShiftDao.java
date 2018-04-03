package com.thirstteacafe.employees.shifts;

public interface ShiftDao {

	void createShift(ShiftData shift);
	
	ShiftData getShiftByID(int shiftID);

	void deleteShiftByID(int shiftID);
	
}
