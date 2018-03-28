package com.thirstteacafe.employees.login;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.thirstteacafe.employees.exceptions.ValidationException;
import com.thirstteacafe.employees.exceptions.AuthenticationException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginServiceTest {

	@Autowired
	private LoginService loginService;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RegisterData createTestRegisterData() {
		RegisterData rd = new RegisterData();
		rd.setFirstname("Vincent");
		rd.setLastname("Nguyen");
		rd.setUsername("vlnguyen");
		rd.setPassword("SimplyBoba");
		return rd;
	}
	
	private LoginData createTestLoginDataFromRegisterData(RegisterData rd) {
		LoginData ld = new LoginData();
		ld.setUsername(rd.getUsername());
		ld.setPassword(rd.getPassword());
		return ld;
	}
	
	public void deleteTestUserFromDatabase() {
		jdbcTemplate.update(
				"DELETE FROM employees WHERE emp_username = 'vlnguyen'");
	}
	
	/*
	 * Attempt to register a new user. 
	 */
	@Before
	public void registerUserBefore() {
	}
	@Test
	public void registerUser() {
		boolean registerSuccess = true;
		RegisterData rd = createTestRegisterData();
		try {
			loginService.register(rd);
		}
		catch (ValidationException ve)
		{
			registerSuccess = false;
		}
		assertTrue(registerSuccess);
	}
	@After
	public void registerUserAfter() {
		deleteTestUserFromDatabase();
	}
	
	/*
	 * Attempt to register a duplicate user.
	 * It should not be possible to create multiple
	 * users with the same username.
	 */
	@Before
	public void registerDuplicateUserBefore() {
	}
	@Test
	public void registerDuplicateUser() {
		boolean registerSuccess = true;
		RegisterData rd = createTestRegisterData();
		try {
			loginService.register(rd);
			loginService.register(rd);
		}
		catch (ValidationException ve)
		{
			registerSuccess = false;
		}
		assertFalse(registerSuccess);
	}
	@After
	public void registerDuplicateUserAfter() {
		jdbcTemplate.update(
			"DELETE FROM employees WHERE emp_username = 'vlnguyen'");
	}
	
	/*
	 * Create a user and attempt to login.
	 */
	@Before
	public void registerAndLoginBefore() {
	}
	@Test
	public void registerAndLogin() {
		boolean loginSuccess = true;
		RegisterData rd = createTestRegisterData();
		LoginData ld = createTestLoginDataFromRegisterData(rd);
		try {
			loginService.register(rd);
			loginService.login(ld);
		}
		catch (ValidationException ve) {
			/* registration should succeed */
		}
		catch (AuthenticationException ae) {
			loginSuccess = false;
		}
		assertTrue(loginSuccess);
	}
	@After
	public void registerAndLoginAfter() {
		deleteTestUserFromDatabase();
	}
	
	/*
	 * Create a user and attempt to login.
	 */
	@Test
	public void loginWithoutRegistration() {
		boolean loginSuccess = true;
		RegisterData rd = createTestRegisterData();
		LoginData ld = createTestLoginDataFromRegisterData(rd);
		try {
			loginService.login(ld);
		}
		catch (AuthenticationException ae) {
			loginSuccess = false;
		}
		assertFalse(loginSuccess);
	}
	
	/*
	 * Login with a bad username.
	 */
	@Before
	public void loginBadUsernameBefore() {
	}
	@Test
	public void loginBadUsername() {
		boolean loginSuccess = true;
		RegisterData rd = createTestRegisterData();
		LoginData ld = createTestLoginDataFromRegisterData(rd);
		ld.setUsername("nhippen");
		try {
			loginService.register(rd);
			loginService.login(ld);
		}
		catch (ValidationException ve) {
			/* registration should succeed */
		}
		catch (AuthenticationException ae) {
			loginSuccess = false;
		}
		assertFalse(loginSuccess);
	}
	@After
	public void loginBadUsernameAfter() {
		deleteTestUserFromDatabase();
	}
	
	/*
	 * Login with a bad password.
	 */
	@Before
	public void loginBadPasswordBefore() {
	}
	@Test
	public void loginBadPassword() {
		boolean loginSuccess = true;
		RegisterData rd = createTestRegisterData();
		LoginData ld = createTestLoginDataFromRegisterData(rd);
		ld.setPassword("SimplyScores");
		try {
			loginService.register(rd);
			loginService.login(ld);
		}
		catch (ValidationException ve) {
			/* registration should succeed */
		}
		catch (AuthenticationException ae) {
			loginSuccess = false;
		}
		assertFalse(loginSuccess);
	}
	@After
	public void loginBadPasswordAfter() {
		deleteTestUserFromDatabase();
	}

}