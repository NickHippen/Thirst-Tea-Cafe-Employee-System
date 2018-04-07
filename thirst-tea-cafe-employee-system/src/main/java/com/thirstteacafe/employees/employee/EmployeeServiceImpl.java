package com.thirstteacafe.employees.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thirstteacafe.employees.dto.Availability;
import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.Shift;
import com.thirstteacafe.employees.dto.DailyAvailability;
import com.thirstteacafe.employees.timeslot.TimeslotService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public Employee getEmployee(Long employeeId) {
		return employeeDao.getEmployee(employeeId);
	}
	
	@Override
	public boolean checkAvailabilityForShift(Employee employee, Shift shift) {
		 List<DailyAvailability> dayAvailability = employee.getAvailability().get(shift.getDayOfWeek());
		 if (dayAvailability != null) {
			 for (DailyAvailability availabilityRange : dayAvailability) {
				 int availableSlotFrom = availabilityRange.getFromTimeslot();
				 int availableSlotTo = availabilityRange.getToTimeslot();
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
	public Employee addAvailability(Long employeeId, Availability availability) {
		employeeDao.addAvailability(employeeId, availability);
		return getEmployee(employeeId);
	}

	@Override
	public Employee deleteAvailability(Long employeeId, Long availabilityId) {
		employeeDao.deleteAvailability(employeeId, availabilityId);
		return getEmployee(employeeId);
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
