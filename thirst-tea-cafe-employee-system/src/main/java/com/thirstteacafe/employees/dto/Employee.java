package com.thirstteacafe.employees.dto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

public class Employee {

	private String name;
	private boolean canLift;
	private boolean admin;
	private boolean foodMaker;
	private boolean drinkMaker;
	private Integer maxHours;
	private Integer minHours;

	private Availability availability;

	public Employee() {
	}

	public Employee(String name, boolean canLift, boolean admin, boolean foodMaker, boolean drinkMaker, Integer minHours,
			Integer maxHours) {
		this.name = name;
		this.canLift = canLift;
		this.admin = admin;
		this.foodMaker = foodMaker;
		this.drinkMaker = drinkMaker;
		this.minHours = minHours;
		this.maxHours = maxHours;
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

	public boolean isFoodMaker() {
		return foodMaker;
	}

	public void setFoodMaker(boolean foodMaker) {
		this.foodMaker = foodMaker;
	}

	public boolean isDrinkMaker() {
		return drinkMaker;
	}

	public void setDrinkMaker(boolean drinkMaker) {
		this.drinkMaker = drinkMaker;
	}

	public Integer getMaxHours() {
		return maxHours;
	}

	public void setMaxHours(Integer maxHours) {
		this.maxHours = maxHours;
	}

	public Integer getMinHours() {
		return minHours;
	}

	public void setMinHours(Integer minHours) {
		this.minHours = minHours;
	}

	public Map<DayOfWeek, List<Pair<LocalTime, LocalTime>>> getAvailability() {
		return availability;
	}

	public void setAvailability(Availability availability) {
		this.availability = availability;
	}

	public void addAvailability(DayOfWeek dayOfWeek, Pair<LocalTime, LocalTime> availabilityRange) {
		if (availability == null) {
			availability = new Availability();
		}
		List<Pair<LocalTime, LocalTime>> dayAvailability = availability.getOrDefault(dayOfWeek,
				new ArrayList<Pair<LocalTime, LocalTime>>());
		dayAvailability.add(availabilityRange);
		availability.put(dayOfWeek, dayAvailability);
	}

}
