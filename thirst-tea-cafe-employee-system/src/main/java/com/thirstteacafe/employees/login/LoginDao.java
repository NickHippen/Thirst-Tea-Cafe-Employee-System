package com.thirstteacafe.employees.login;

import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.exceptions.AuthenticationException;

public interface LoginDao {

	Employee login(LoginData login) throws AuthenticationException;
	void register(RegisterData register);
	boolean isExistingUserName(String userName);
	
}
