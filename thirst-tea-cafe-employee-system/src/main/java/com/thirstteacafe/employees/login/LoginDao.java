package com.thirstteacafe.employees.login;

import com.thirstteacafe.employees.exceptions.AuthenticationException;
import com.thirstteacafe.employees.exceptions.ValidationException;

public interface LoginDao {

	void login(LoginData login) throws AuthenticationException;
	void register(RegisterData register);
	boolean isExistingUserName(String userName);
	
}
