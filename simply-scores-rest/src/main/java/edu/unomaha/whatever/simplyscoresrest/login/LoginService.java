package edu.unomaha.whatever.simplyscoresrest.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unomaha.whatever.simplyscoresrest.exceptions.AuthenticationException;
import edu.unomaha.whatever.simplyscoresrest.exceptions.ValidationException;

@Service
public class LoginService {

	@Autowired
	private LoginDao loginDao;
	
	public void login(LoginData login) throws AuthenticationException {
		loginDao.login(login);
	}
	
	public void register(RegisterData register) throws ValidationException {
		if (loginDao.isExistingUserName(register.getUserName())) {
			throw new ValidationException("Username already exists");
		}
		loginDao.register(register);
	}
	
}
