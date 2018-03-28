package com.thirstteacafe.employees.login;

public class RegisterData {

	private String username;
	private String password;
	private String firstname;
	private String lastname;
	
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


	@Override
	public String toString() {
		return String.format(
			"LoginRequest [username=%s, password=%s, firstname=%s, lastname=%s]",
			username, password, firstname, lastname);
	}

}
