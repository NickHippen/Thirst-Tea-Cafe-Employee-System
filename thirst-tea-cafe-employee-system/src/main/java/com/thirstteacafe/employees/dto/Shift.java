package com.thirstteacafe.employees.dto;

import java.time.DayOfWeek;

public class Shift {

	private DayOfWeek dayOfWeek;
	private Integer timeFrom;
	private Integer timeTo;

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Integer getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(Integer timeFrom) {
		this.timeFrom = timeFrom;
	}

	public Integer getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(Integer timeTo) {
		this.timeTo = timeTo;
	}

}
