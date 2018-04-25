package com.thirstteacafe.employees;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thirstteacafe.employees.schedule.ASPScheduleGenerator;
import com.thirstteacafe.employees.schedule.JaCoPScheduleGenerator;
import com.thirstteacafe.employees.schedule.ScheduleGenerator;

@Configuration
public class ScheduleGeneratorConfig {

	@Bean
	public ScheduleGenerator scheduleGenerator() {
		return new ASPScheduleGenerator();
	}
	
	@Bean
	public ASPScheduleGenerator aspScheduleGenerator() {
		return new ASPScheduleGenerator();
	}
	
	@Bean
	public JaCoPScheduleGenerator jacopScheduleGenerator() {
		return new JaCoPScheduleGenerator();
	}

}
