package com.thirstteacafe.employees.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thirstteacafe.employees.dto.WeeklySchedule;

@CrossOrigin
@RestController
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;

	/* READ */
	@RequestMapping(value="/schedule", method=RequestMethod.GET)
	public WeeklySchedule getSchedule(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
		return scheduleService.getSchedule(date);
	}

}
