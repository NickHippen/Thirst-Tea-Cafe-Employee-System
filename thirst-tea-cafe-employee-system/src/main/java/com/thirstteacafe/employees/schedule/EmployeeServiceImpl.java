package com.thirstteacafe.employees.schedule;

import java.time.LocalTime;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.Shift;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Override
	public boolean checkAvailabilityForShift(Employee employee, Shift shift) {
		 List<Pair<LocalTime, LocalTime>> dayAvailability = employee.getAvailability().get(shift.getDayOfWeek());
		 // TODO Convert LocalTime to timeslot
		 return false;
	}
	
}
