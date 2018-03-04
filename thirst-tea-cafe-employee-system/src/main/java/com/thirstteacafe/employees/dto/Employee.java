package com.thirstteacafe.employees.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

public class Employee {

	private String name;
	private boolean canLift;
	private boolean admin;
	private boolean food;
	private boolean drinks;
	
	private Map<DayOfWeek, List<Pair<LocalTime, LocalTime>>> availability;

	public Employee() {
	}
	
	public Employee(String name, boolean canLift, boolean admin, boolean food, boolean drinks) {
		this.name = name;
		this.canLift = canLift;
		this.admin = admin;
		this.food = food;
		this.drinks = drinks;
	}
	
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
	
	public void addAvailability(DayOfWeek dayOfWeek, Pair<LocalTime, LocalTime> availabilityRange) {
		if (availability == null) {
			availability = new HashMap<>();
		}
		List<Pair<LocalTime, LocalTime>> dayAvailability = availability.getOrDefault(dayOfWeek, new ArrayList<Pair<LocalTime, LocalTime>>());
		dayAvailability.add(availabilityRange);
		availability.put(dayOfWeek, dayAvailability);
	}

}
