package edu.unomaha.whatever.simplyscoresrest.login;

import edu.unomaha.whatever.simplyscoresrest.exceptions.AuthenticationException;
import edu.unomaha.whatever.simplyscoresrest.exceptions.ValidationException;

public interface LoginDao {

	void login(LoginData login) throws AuthenticationException;
	void register(RegisterData register);
	boolean isExistingUserName(String userName);
	
}
