package com.thirstteacafe.employees.schedule;

import java.util.List;

import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.ScheduleResult;
import com.thirstteacafe.employees.dto.Shift;

public interface ScheduleService {

	ScheduleResult scheduleEmployees(List<Employee> employees, List<Shift> shifts);
	
	ScheduleResult schedule(String available, String admin, String canLift, String food, String drink, String minHours, String maxHours,
                String minEmployees, String maxEmployees, String time, String adminOnly);

}
