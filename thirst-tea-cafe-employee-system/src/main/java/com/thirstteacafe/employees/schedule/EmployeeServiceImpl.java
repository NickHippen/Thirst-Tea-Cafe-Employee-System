package com.thirstteacafe.employees.schedule;

import java.time.LocalTime;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.Shift;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private TimeslotService timeslotService;
	
	@Override
	public boolean checkAvailabilityForShift(Employee employee, Shift shift) {
		 List<Pair<LocalTime, LocalTime>> dayAvailability = employee.getAvailability().get(shift.getDayOfWeek());
		 for (Pair<LocalTime, LocalTime> availabilityRange : dayAvailability) {
			 int availableSlotFrom = timeslotService.convertLocalTime(availabilityRange.getLeft());
			 int availableSlotTo = timeslotService.convertLocalTime(availabilityRange.getRight());
			 if (shift.getStartTimeslot() >= availableSlotFrom && shift.getEndTimeslot() <= availableSlotTo) {
				 return true;
			 }
		 }
		 return false;
	}
	
}
