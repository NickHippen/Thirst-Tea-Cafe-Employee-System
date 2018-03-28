package com.thirstteacafe.employees.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thirstteacafe.employees.exceptions.AuthenticationException;
import com.thirstteacafe.employees.exceptions.ValidationException;

@Service
public class LoginService {

	@Autowired
	private LoginDao loginDao;
	
	public void login(LoginData login) throws AuthenticationException {
		loginDao.login(login);
	}
	
	public void register(RegisterData register) throws ValidationException {
		if (loginDao.isExistingUserName(register.getUsername())) {
			throw new ValidationException("Username already exists");
		}
		loginDao.register(register);
	}
	
}
