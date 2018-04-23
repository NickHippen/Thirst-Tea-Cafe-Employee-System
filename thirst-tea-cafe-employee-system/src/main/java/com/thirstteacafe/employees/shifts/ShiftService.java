package com.thirstteacafe.employees.shifts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

	public List<ShiftData> getAllShifts() {
		return shiftDao.getAllShifts();
	}
	
	public void deleteShiftByID(int shiftID) {
		shiftDao.deleteShiftByID(shiftID);
	}
}
