package com.thirstteacafe.employees.employee;

import java.time.LocalTime;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thirstteacafe.employees.dto.Availability;
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

	@Override
	public void createEmployee(Employee employee) {
		employeeDao.createEmployee(employee);
	}

	@Override
	public void deleteEmployee(Long employeeId) {
		deleteAllAvailability(employeeId);
		employeeDao.deleteEmployee(employeeId);
	}

	@Override
	public void addAvailability(Long employeeId, Availability availability) {
		employeeDao.addAvailability(employeeId, availability);
	}

	@Override
	public void deleteAvailability(Long employeeId, Long availabilityId) {
		employeeDao.deleteAvailability(employeeId, availabilityId);
	}
	
	@Override
	public void deleteAllAvailability(Long employeeId) {
		employeeDao.deleteAllAvailability(employeeId);
	}
	
	@Override
	public Availability getAvailability(Long employeeId) {
		return employeeDao.getAvailability(employeeId);
	}

	@Override
	public void updateEmployee(Long employeeId, Employee employee) {
		employeeDao.updateEmployee(employeeId, employee);
	}
	
}
