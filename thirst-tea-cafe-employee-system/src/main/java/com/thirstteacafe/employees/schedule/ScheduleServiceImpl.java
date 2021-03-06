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
	public WeeklySchedule getSchedule(Date date) throws ScheduleException {
//		try {
//			return generateSchedule(date);
//		} catch (ScheduleException e) {
//			e.printStackTrace();
//		}
//		return null;
		return scheduleDao.getSchedule(getStartOfWeek(date));
	}
	
	@Override
	public WeeklySchedule generateSchedule(Date date) throws ScheduleException {
		Date startOfLastWeek = getStartOfLastWeek(date);
		List<Employee> employees = employeeService.getAllEmployees();
		List<Shift> shifts = shiftService.getAllShifts();
		WeeklySchedule previousSchedule;
		try {
			previousSchedule = getSchedule(startOfLastWeek);
		} catch (ScheduleException e) {
			previousSchedule = null;
		}
		return scheduleGenerator.scheduleEmployees(employees, shifts, previousSchedule);
	}

	@Override
	public void publishSchedule(Date date, WeeklySchedule schedule) throws ScheduleException {
		Date startOfWeek = getStartOfWeek(date);
		scheduleDao.publishSchedule(startOfWeek, schedule);
	}

	@Override
	public void deleteSchedule(Date date) throws ScheduleException {
		Date startOfWeek = getStartOfWeek(date);
		scheduleDao.deleteSchedule(startOfWeek);
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
	
	private Date getStartOfLastWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getStartOfWeek(date));
		cal.add(Calendar.DAY_OF_WEEK, -1);
		return cal.getTime();
	}

	@Override
	public void updateSchedule(Date date, WeeklySchedule schedule) throws ScheduleException {
		Date startOfWeek = getStartOfWeek(date);
		getSchedule(startOfWeek); // Will throw exception if no schedule
		scheduleDao.updateSchedule(startOfWeek, schedule);
	}

}
