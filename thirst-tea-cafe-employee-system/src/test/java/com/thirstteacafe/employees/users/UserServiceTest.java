package com.thirstteacafe.employees.users;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.thirstteacafe.employees.exceptions.ValidationException;
import com.thirstteacafe.employees.login.LoginService;
import com.thirstteacafe.employees.login.RegisterData;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final int FIRSTNAME = 0;
	private static final int LASTNAME  = 1;
	private static final int USERNAME  = 2;
	private static final int PASSWORD  = 3;

	private String[][] userStrings =  
	{
		{"Vincent", "Nguyen", "vlnguyen", "Vietcong"},
		{"Jon", "Dunham", "jldunham", "CompulsiveSpender"},
		{"Tommy", "Pang", "tpang", "ThaaaankYou"},
		{"Kathy", "Alcazar", "kalcazar", "avaibility"}
	};

	private RegisterData createTestRegisterData() {
		return createTestRegisterData(userStrings[0]);
	}

	private RegisterData createTestRegisterData(String[] vals) {
		RegisterData rd = new RegisterData();
		rd.setFirstname(vals[FIRSTNAME]);
		rd.setLastname(vals[LASTNAME]);
		rd.setUsername(vals[USERNAME]);
		rd.setPassword(vals[PASSWORD]);
		return rd;
	}

	private void registerTestUser() throws ValidationException {
		registerTestUser(createTestRegisterData());
	}
	
	private void registerTestUser(RegisterData rd) throws ValidationException {
		loginService.register(rd);		
	}
	
	public void deleteTestUserFromDatabaseByUsername(String username) {
		jdbcTemplate.update(
				"DELETE FROM employees WHERE emp_username = '" + username + "'");
	}
	
	public void deleteTestUserFromDatabaseByUsername() {
		deleteTestUserFromDatabaseByUsername(createTestRegisterData().getUsername());
	}
	
	/*
	 * Register a new user and search for that user by username. 
	 */
	@Test
	public void searchByValidUsername() {
		try { 
			registerTestUser();
		}
		catch (ValidationException ve) {
			fail("Failed to register user for search.");
		}
		
		UserRequestData urd = new UserRequestData();
		urd.setUsername("vlnguyen");
		List<UserData> results = userService.getUserSearchResult(urd);
		
		assertFalse(results.isEmpty());
		deleteTestUserFromDatabaseByUsername();	
	}

	
	/*
	 * Search for a user that does not exist.
	 */
	@Test
	public void searchByInvalidUsername() {
		UserRequestData urd = new UserRequestData();
		urd.setUsername("vlnguyen");
		List<UserData> results = userService.getUserSearchResult(urd);
		
		assertTrue(results.isEmpty());
	}
	
	/*
	 * Search for a user by their user ID.
	 */
	@Test
	public void searchByValidID() {
		UserData employee = null;
		try { 
			registerTestUser();
			
			UserRequestData urd = new UserRequestData();
			urd.setUsername("vlnguyen");
			List<UserData> results = userService.getUserSearchResult(urd);
			employee = results.get(0);
			
			int id = employee.getId();
			employee = userService.getUserByID(id);
		}
		catch (ValidationException ve) {
			fail("Failed to register user for search.");
		}
		catch (IndexOutOfBoundsException iobe) {
			fail("Failed to search for user by username.");
		}
		
		assertTrue(employee != null);
		deleteTestUserFromDatabaseByUsername();
	}

	/*
	 * Search for a user by a user ID that does not exist.
	 */
	@Test
	public void searchByInvalidID() {
		UserData employee = null;
		try {
			employee = userService.getUserByID(9001);
		}
		catch (IndexOutOfBoundsException iobe) {
			/* user not found */
		}

		assertTrue(employee == null);
	}

	/*
	 * Get a list of all employees.
	 */
	@Test
	public void getAllUsers() {
		try {
			for (String[] vals : userStrings) {
				registerTestUser(createTestRegisterData(vals));
			}
			List<UserData> users = userService.getAllUsers();

			assertEquals(users.size(), userStrings.length);

			for (String[] vals : userStrings) {
				deleteTestUserFromDatabaseByUsername(vals[USERNAME]);
			}
		}
		catch (ValidationException ve) {
			fail("Failed to register user for search.");
		}

	}
}