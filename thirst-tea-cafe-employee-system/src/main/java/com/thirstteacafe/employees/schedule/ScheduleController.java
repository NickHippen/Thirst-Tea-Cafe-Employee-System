package com.thirstteacafe.employees.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thirstteacafe.employees.dto.WeeklySchedule;
import com.thirstteacafe.employees.exceptions.ScheduleException;

@CrossOrigin
@RestController
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;

	/* READ */
	@RequestMapping(value="/schedule", method=RequestMethod.GET)
	public WeeklySchedule getSchedule(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date date) throws ScheduleException {
		return scheduleService.getSchedule(date);
	}
	
	@RequestMapping(value="/schedule/generate", method=RequestMethod.GET)
	public WeeklySchedule generateSchedule(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date date) throws ScheduleException {
		return scheduleService.generateSchedule(date);
	}
	
	@RequestMapping(value="/schedule", method=RequestMethod.POST)
	public void publishSchedule(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date date, @RequestBody WeeklySchedule schedule) throws ScheduleException {
		scheduleService.publishSchedule(date, schedule);
	}
	
	@RequestMapping(value="/schedule", method=RequestMethod.DELETE)
	public void deleteSchedule(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date date) throws ScheduleException {
		scheduleService.deleteSchedule(date);
	}
	
	@RequestMapping(value="/schedule", method=RequestMethod.PUT)
	public void updateSchedule(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date date, @RequestBody WeeklySchedule schedule) throws ScheduleException {
		scheduleService.updateSchedule(date, schedule);
	}

}
