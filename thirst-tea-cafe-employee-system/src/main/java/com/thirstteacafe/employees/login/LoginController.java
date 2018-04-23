package com.thirstteacafe.employees.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thirstteacafe.employees.dto.Employee;
import com.thirstteacafe.employees.exceptions.AuthenticationException;
import com.thirstteacafe.employees.exceptions.ValidationException;

@CrossOrigin
@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public Employee login(@RequestBody LoginData login) throws AuthenticationException {
		return loginService.login(login);
	}
	
	@RequestMapping(value="/login/register", method=RequestMethod.POST)
	public void register(@RequestBody RegisterData register) throws ValidationException {
		loginService.register(register);
	}
	
}
