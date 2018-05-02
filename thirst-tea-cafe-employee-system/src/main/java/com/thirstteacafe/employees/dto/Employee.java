package com.thirstteacafe.employees.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DTO object describing an employee
 */
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

	public Map<DayOfWeek, List<DailyAvailability>> getAvailability() {
		return availability;
	}

	public void setAvailability(Availability availability) {
		this.availability = availability;
	}

	public void addAvailability(DayOfWeek dayOfWeek, DailyAvailability availabilityRange) {
		if (availability == null) {
			availability = new Availability();
		}
		List<DailyAvailability> dayAvailability = availability.getOrDefault(dayOfWeek,
				new ArrayList<DailyAvailability>());
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (admin ? 1231 : 1237);
		result = prime * result + ((availability == null) ? 0 : availability.hashCode());
		result = prime * result + (canLift ? 1231 : 1237);
		result = prime * result + (drinkMaker ? 1231 : 1237);
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + (foodMaker ? 1231 : 1237);
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((maxHours == null) ? 0 : maxHours.hashCode());
		result = prime * result + ((minHours == null) ? 0 : minHours.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (admin != other.admin)
			return false;
		if (availability == null) {
			if (other.availability != null)
				return false;
		} else if (!availability.equals(other.availability))
			return false;
		if (canLift != other.canLift)
			return false;
		if (drinkMaker != other.drinkMaker)
			return false;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (foodMaker != other.foodMaker)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (maxHours == null) {
			if (other.maxHours != null)
				return false;
		} else if (!maxHours.equals(other.maxHours))
			return false;
		if (minHours == null) {
			if (other.minHours != null)
				return false;
		} else if (!minHours.equals(other.minHours))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
