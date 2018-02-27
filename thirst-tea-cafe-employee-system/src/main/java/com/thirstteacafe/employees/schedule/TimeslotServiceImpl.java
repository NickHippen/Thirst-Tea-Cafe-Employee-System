package com.thirstteacafe.employees.schedule;

import java.time.LocalTime;

import org.springframework.stereotype.Service;

@Service
public class TimeslotServiceImpl implements TimeslotService {

	@Override
	public int convertLocalTime(LocalTime time) {
		return time.getHour() * 2 + time.getMinute() / 30;
	}
	
}
