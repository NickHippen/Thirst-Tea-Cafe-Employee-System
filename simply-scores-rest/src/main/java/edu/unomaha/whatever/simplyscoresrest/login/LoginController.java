package edu.unomaha.whatever.simplyscoresrest.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.unomaha.whatever.simplyscoresrest.exceptions.AuthenticationException;
import edu.unomaha.whatever.simplyscoresrest.exceptions.ValidationException;

@CrossOrigin
@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public void login(@RequestBody LoginData login) throws AuthenticationException {
		loginService.login(login);
	}
	
	@RequestMapping(value="/login/register", method=RequestMethod.POST)
	public void register(@RequestBody RegisterData register) throws ValidationException {
		loginService.register(register);
	}
	
}
