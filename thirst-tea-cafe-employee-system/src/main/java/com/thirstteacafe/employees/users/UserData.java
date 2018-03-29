package com.thirstteacafe.employees.users;

public class UserData {

	private int id;
	private String username;
	private String firstname;
	private String lastname;
	private float minHours;
	private float maxHours;
	private boolean canLift;
	private boolean food;
	private boolean drinks;
	private boolean admin;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public float getMinHours() {
		return minHours;
	}
	public void setMinHours(float minHours) {
		this.minHours = minHours;
	}
	
	public float getMaxHours() {
		return maxHours;
	}
	public void setMaxHours(float maxHours) {
		this.maxHours = maxHours;
	}
	
	public boolean canLift() {
		return canLift;
	}
	public void setCanLift(boolean canLift) {
		this.canLift = canLift;
	}
	
	public boolean canMakeFood() {
		return food;
	}
	public void setFood(boolean food) {
		this.food = food;
	}
	
	public boolean canMakeDrinks() {
		return drinks;
	}
	public void setDrinks(boolean drinks) {
		this.drinks = drinks;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	@Override
	public String toString() {
		String s = "[UserData]\n";
		s += String.format("id        : %d\n", id);
		s += String.format("username  : %s\n", username);
		s += String.format("firstname : %s\n", firstname);
		s += String.format("lastname  : %s\n", lastname);
		s += String.format("minHours  : %.1f\n", minHours);
		s += String.format("maxHours  : %.1f\n", maxHours);
		s += String.format("canLift   : %s\n", canLift);
		s += String.format("food      : %s\n", food);
		s += String.format("drinks    : %s\n", drinks);
		s += String.format("admin     : %s", admin);
		return s;
	}
	
}
