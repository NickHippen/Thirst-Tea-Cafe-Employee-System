package com.thirstteacafe.employees.dto;

public class Shift {
	private DayOfWeek dayOfWeek;
	private Integer startTimeslot;
	private Integer endTimeslot;
	private Integer minEmployees;
	private Integer maxEmployees;
	private boolean adminOnly;

	public Shift() {
	}

	public Shift(DayOfWeek dayOfWeek, Integer startTimeslot, Integer endTimeslot) {
		this(dayOfWeek, startTimeslot, endTimeslot, 2, 2, false);
	}

	public Shift(DayOfWeek dayOfWeek, Integer startTimeslot, Integer endTimeslot, Integer minEmployees,
			Integer maxEmployees, boolean adminOnly) {
		this.dayOfWeek = dayOfWeek;
		this.startTimeslot = startTimeslot;
		this.endTimeslot = endTimeslot;
		this.minEmployees = minEmployees;
		this.maxEmployees = maxEmployees;
		this.adminOnly = adminOnly;
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

	public Integer getMinEmployees() {
		return minEmployees;
	}

	public void setMinEmployees(Integer minEmployees) {
		this.minEmployees = minEmployees;
	}

	public Integer getMaxEmployees() {
		return maxEmployees;
	}

	public void setMaxEmployees(Integer maxEmployees) {
		this.maxEmployees = maxEmployees;
	}

	public boolean isAdminOnly() {
		return adminOnly;
	}

	public void setAdminOnly(boolean adminOnly) {
		this.adminOnly = adminOnly;
	}

	public int getTimeLength() {
		return getEndTimeslot() - getStartTimeslot();
	}

}
