package com.thirstteacafe.employees.schedule;

import java.util.Date;
import java.util.List;

import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.dto.ScheduleResult;
import com.thirstteacafe.employees.dto.Shift;
import com.thirstteacafe.employees.dto.WeeklySchedule;
import com.thirstteacafe.employees.exceptions.ScheduleException;

public interface ScheduleService {

	/**
     * 
     * @param employees a list of employees that will be scheduled
     * @param shifts a list of shifts that need employees
     * @return an assignment of employees to shifts 
     */
	ScheduleResult scheduleEmployees(List<Employee> employees, List<Shift> shifts);
	
	ScheduleResult schedule(String available, String admin, String canLift, String food, String drink, String minHours, String maxHours,
                String minEmployees, String maxEmployees, String time, String adminOnly, String days);

	WeeklySchedule getSchedule(Date date);

	WeeklySchedule generateSchedule(Date date) throws ScheduleException;

}
