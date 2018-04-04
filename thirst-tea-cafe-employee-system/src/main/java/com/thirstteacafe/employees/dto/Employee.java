package com.thirstteacafe.employees.dto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

public class Employee {

	private Long employeeId;
	private String firstName;
	private String lastName;
	private boolean canLift;
	private boolean admin;
	private boolean foodMaker;
	private boolean drinkMaker;
	private Integer maxHours;
	private Integer minHours;
	private String username;
	private String password;

	private Availability availability;

	public Employee() {
	}

	public Employee(String firstName, String lastName, boolean canLift, boolean admin, boolean foodMaker, boolean drinkMaker, Integer minHours,
			Integer maxHours) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.canLift = canLift;
		this.admin = admin;
		this.foodMaker = foodMaker;
		this.drinkMaker = drinkMaker;
		this.minHours = minHours;
		this.maxHours = maxHours;
	}
	
	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
