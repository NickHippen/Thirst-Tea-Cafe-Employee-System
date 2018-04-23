package com.thirstteacafe.employees.shifts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class ShiftController {

	@Autowired
	private ShiftService shiftService;

	/* CREATE */
	@RequestMapping(value="/shift", method=RequestMethod.POST)
	public void createShift(@RequestBody ShiftData shift){
		shiftService.createShift(shift);
	}

	/* READ */
	@RequestMapping(value="/shift/", method=RequestMethod.GET)
	public List<ShiftData> getAllShifts() {
		return shiftService.getAllShifts();
	}

	@RequestMapping(value="/shift/{shiftID}", method=RequestMethod.GET)
	public ShiftData getShift(@PathVariable Integer shiftID) {
		return shiftService.getShiftByID(shiftID);
	}

	/* DELETE */
	@RequestMapping(value="/shift/{shiftID}", method=RequestMethod.DELETE)
	public void deleteShiftByID(@PathVariable Integer shiftID) {
		shiftService.deleteShiftByID(shiftID);
	}

}
