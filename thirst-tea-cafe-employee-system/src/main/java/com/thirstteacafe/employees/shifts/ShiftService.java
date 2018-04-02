package com.thirstteacafe.employees.shifts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShiftService {
	
	@Autowired
	private ShiftDao shiftDao;

	public void createShift(ShiftData shift) {
		shiftDao.createShift(shift);
	}

	public ShiftData getShiftByID(int shiftID) {
		return shiftDao.getShiftByID(shiftID);
	}
	
}
