package com.thirstteacafe.employees.employee;

import java.time.LocalTime;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.Shift;
import com.thirstteacafe.employees.timeslot.TimeslotService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private TimeslotService timeslotService;
	
	@Override
	public Employee getEmployee(Long employeeId) {
		return employeeDao.getEmployee(employeeId);
	}
	
	@Override
	public boolean checkAvailabilityForShift(Employee employee, Shift shift) {
		 List<Pair<LocalTime, LocalTime>> dayAvailability = employee.getAvailability().get(shift.getDayOfWeek());
		 if (dayAvailability != null) {
			 for (Pair<LocalTime, LocalTime> availabilityRange : dayAvailability) {
				 int availableSlotFrom = timeslotService.convertLocalTime(availabilityRange.getLeft());
				 int availableSlotTo = timeslotService.convertLocalTime(availabilityRange.getRight());
				 if (shift.getStartTimeslot() >= availableSlotFrom && shift.getEndTimeslot() <= availableSlotTo) {
					 return true;
				 }
			 }
		 }
		 return false;
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeDao.getAllEmployees();
	}
	
}
