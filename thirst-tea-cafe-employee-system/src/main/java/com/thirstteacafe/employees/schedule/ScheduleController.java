package com.thirstteacafe.employees.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thirstteacafe.employees.dto.WeeklySchedule;

@CrossOrigin
@RestController
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;

	/* READ */
	@RequestMapping(value="/getSchedule", method=RequestMethod.GET)
	public WeeklySchedule getSchedule() {
		return scheduleService.getSchedule();
	}

}
