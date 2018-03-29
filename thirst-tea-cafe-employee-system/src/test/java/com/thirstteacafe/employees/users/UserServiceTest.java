package com.thirstteacafe.employees.users;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.thirstteacafe.employees.exceptions.ValidationException;
import com.thirstteacafe.employees.login.LoginData;
import com.thirstteacafe.employees.login.LoginService;
import com.thirstteacafe.employees.login.RegisterData;
import com.thirstteacafe.employees.exceptions.AuthenticationException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
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
	
	
	private void registerTestUser() throws ValidationException {
		RegisterData rd = createTestRegisterData();
		loginService.register(rd);		
	}
	
	public void deleteTestUserFromDatabase() {
		jdbcTemplate.update(
				"DELETE FROM employees WHERE emp_username = 'vlnguyen'");
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
		deleteTestUserFromDatabase();	
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
		deleteTestUserFromDatabase();
	}

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

}