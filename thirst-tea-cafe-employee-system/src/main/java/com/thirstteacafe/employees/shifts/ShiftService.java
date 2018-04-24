package com.thirstteacafe.employees.shifts;

import com.thirstteacafe.employees.dto.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftService {
	
	@Autowired
	private ShiftDao shiftDao;

	public void createShift(Shift shift) {
		shiftDao.createShift(shift);
	}

	public Shift getShiftByID(int shiftID) {
		return shiftDao.getShiftByID(shiftID);
	}

	public List<Shift> getAllShifts() {
		return shiftDao.getAllShifts();
	}
	
	public void deleteShiftByID(int shiftID) {
		shiftDao.deleteShiftByID(shiftID);
	}
}
