package com.thirstteacafe.employees.dto;

/**
 * DTO object describing a shift
 */
public class Shift {

	private Integer id;
	private DayOfWeek dayOfWeek;
	private Integer startTimeslot;
	private Integer endTimeslot;
	private Integer numEmployees;
	private Integer numAdmins;

	public Shift() {
	}

	public Shift(DayOfWeek dayOfWeek, Integer startTimeslot, Integer endTimeslot) {
		this(dayOfWeek, startTimeslot, endTimeslot, 2, 0);
	}

	public Shift(DayOfWeek dayOfWeek, 
				 Integer startTimeslot, 
				 Integer endTimeslot, 
				 Integer numEmployees, 
				 Integer numAdmins) {
		this.dayOfWeek = dayOfWeek;
		this.startTimeslot = startTimeslot;
		this.endTimeslot = endTimeslot;
		this.numEmployees = numEmployees;
		this.numAdmins = numAdmins;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getNumEmployees() {
		return numEmployees;
	}

	public void setNumEmployees(Integer numEmployees) {
		this.numEmployees = numEmployees;
	}

	public Integer getNumAdmins() {
		return numAdmins;
	}

	public void setNumAdmins(Integer numAdmins) {
		this.numAdmins = numAdmins;
	}

	public int getTimeLength() {
		return getEndTimeslot() - getStartTimeslot();
	}

}
