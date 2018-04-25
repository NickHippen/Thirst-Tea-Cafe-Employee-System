package com.thirstteacafe.employees.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.Shift;
import com.thirstteacafe.employees.dto.WeeklySchedule;
import com.thirstteacafe.employees.employee.EmployeeService;
import com.thirstteacafe.employees.exceptions.ScheduleException;
import com.thirstteacafe.employees.shifts.ShiftService;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	
	@Autowired
	private ScheduleGenerator scheduleGenerator;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private ShiftService shiftService;
	@Autowired
	private ScheduleDao scheduleDao;

	@Override
	public WeeklySchedule getSchedule(Date date) {
		try {
			return generateSchedule(date);
		} catch (ScheduleException e) {
			e.printStackTrace();
		}
		return null;
//		return scheduleDao.getSchedule(getStartOfWeek(date));
	}
	
	@Override
	public WeeklySchedule generateSchedule(Date date) throws ScheduleException {
		Date startOfWeek = getStartOfWeek(date); // TODO Use me
		List<Employee> employees = employeeService.getAllEmployees();
		List<Shift> shifts = shiftService.getAllShifts();
		return scheduleGenerator.scheduleEmployees(employees, shifts);
	}
	
	private Date getStartOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		return cal.getTime();
	}

}
