package com.thirstteacafe.employees.dto;

import java.time.DayOfWeek;

public class Shift {

	private DayOfWeek dayOfWeek;
	private Integer startTimeslot;
	private Integer endTimeslot;

	public Shift() {
	}
	
	public Shift(DayOfWeek dayOfWeek, Integer startTimeslot, Integer endTimeslot) {
		this.dayOfWeek = dayOfWeek;
		this.startTimeslot = startTimeslot;
		this.endTimeslot = endTimeslot;
	}
	
	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Integer getStartTimeslot() {
		return startTimeslot;
	}

	public void setStartTimeslot(Integer startTimeslot) {
		this.startTimeslot = startTimeslot;
	}

	public Integer getEndTimeslot() {
		return endTimeslot;
	}

	public void setEndTimeslot(Integer endTimeslot) {
		this.endTimeslot = endTimeslot;
	}

}
