package com.thirstteacafe.employees.shifts;

import com.thirstteacafe.employees.dto.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftService {
	
	@Autowired
	private ShiftDao shiftDao;

	/**
	 * Creates a shift
	 * @param shift the shift to create
	 */
	public void createShift(Shift shift) {
		shiftDao.createShift(shift);
	}

	/**
	 * Gets a shift by id
	 * @param shiftID the id of the shift to get
	 * @return the shift for the id, or null if not found
	 */
	public Shift getShiftByID(int shiftID) {
		return shiftDao.getShiftByID(shiftID);
	}

	/**
	 * Gets all shifts
	 * @return all shifts
	 */
	public List<Shift> getAllShifts() {
		return shiftDao.getAllShifts();
	}
	
	/**
	 * Deletes a shift
	 * @param shiftID the id of the shift to delete
	 */
	public void deleteShiftByID(int shiftID) {
		shiftDao.deleteShiftByID(shiftID);
	}
}
