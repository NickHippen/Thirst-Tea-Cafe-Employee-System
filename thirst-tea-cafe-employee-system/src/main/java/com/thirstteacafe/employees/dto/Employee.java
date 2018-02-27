package com.thirstteacafe.employees.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public class Employee {

	private String name;
	private boolean canLift;
	private boolean admin;
	private boolean food;
	private boolean drinks;
	
	private Map<DayOfWeek, List<Pair<LocalTime, LocalTime>>> availability;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCanLift() {
		return canLift;
	}

	public void setCanLift(boolean canLift) {
		this.canLift = canLift;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isFood() {
		return food;
	}

	public void setFood(boolean food) {
		this.food = food;
	}

	public boolean isDrinks() {
		return drinks;
	}

	public void setDrinks(boolean drinks) {
		this.drinks = drinks;
	}

	public Map<DayOfWeek, List<Pair<LocalTime, LocalTime>>> getAvailability() {
		return availability;
	}

	public void setAvailability(Map<DayOfWeek, List<Pair<LocalTime, LocalTime>>> availability) {
		this.availability = availability;
	}
	
	/**
	 * Checks whether or not employee has work availability for shift
	 * @param employee
	 * @param shift
	 * @return whether or not employee has work availability for shift
	 */
	public boolean checkAvailabilityForShift(Employee employee, Shift shift) {
		 List<Pair<LocalTime, LocalTime>> dayAvailability = employee.getAvailability().get(shift.getDayOfWeek());
		 // TODO Convert LocalTime to timeslot
		 return false;
	}

}
