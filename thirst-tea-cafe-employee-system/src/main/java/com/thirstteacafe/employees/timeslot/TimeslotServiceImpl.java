package com.thirstteacafe.employees.timeslot;

import java.time.LocalTime;

import org.springframework.stereotype.Service;

@Service
public class TimeslotServiceImpl implements TimeslotService {

	@Override
	public int convertLocalTime(LocalTime time) {
		return time.getHour() * 2 + time.getMinute() / 30;
	}
	
	@Override
	public LocalTime convertTimeslot(int timeslot) {
		return LocalTime.of(timeslot / 2, (timeslot % 2) * 30);
	}
	
}
