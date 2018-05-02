package com.thirstteacafe.employees.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.exceptions.AuthenticationException;
import com.thirstteacafe.employees.exceptions.ValidationException;

@Service
public class LoginService {

	@Autowired
	private LoginDao loginDao;
	
	/**
	 * Logs an employee in
	 * @param login the login credentials
	 * @return the employee that logged in
	 * @throws AuthenticationException if the credentials are invalid
	 */
	public Employee login(LoginData login) throws AuthenticationException {
		return loginDao.login(login);
	}
	
	/**
	 * Registers an employee
	 * @param register the new credentials for the employee
	 * @throws ValidationException if the username already exists
	 */
	public void register(RegisterData register) throws ValidationException {
		if (loginDao.isExistingUserName(register.getUsername())) {
			throw new ValidationException("Username already exists");
		}
		loginDao.register(register);
	}
	
}
