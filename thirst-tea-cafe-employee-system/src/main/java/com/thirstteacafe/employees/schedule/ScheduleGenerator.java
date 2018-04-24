package com.thirstteacafe.employees.schedule;

import java.util.List;

import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.ScheduleResult;
import com.thirstteacafe.employees.dto.Shift;
import com.thirstteacafe.employees.dto.WeeklySchedule;
import com.thirstteacafe.employees.exceptions.ScheduleException;

public interface ScheduleGenerator {

	WeeklySchedule scheduleEmployees(List<Employee> employees, List<Shift> shifts) throws ScheduleException;
	
}
