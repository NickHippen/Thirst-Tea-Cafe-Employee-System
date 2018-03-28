package com.thirstteacafe.employees.login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.thirstteacafe.employees.exceptions.AuthenticationException;

@Component
public class JdbcLoginDao implements LoginDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void login(LoginData login) throws AuthenticationException {
		List<Integer> userIds = jdbcTemplate.query(
			"SELECT M.emp_id FROM employees M WHERE M.emp_username=? AND M.emp_password=?",
			new Object[] { login.getUsername(), login.getPassword() },
			new RowMapper<Integer>() {
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getInt("emp_id");
				}
			});
		if (userIds.isEmpty()) {
			throw new AuthenticationException("Invalid login credentials");
		}
	}

	@Override
	public void register(RegisterData register) {		
		jdbcTemplate.update(
			"INSERT INTO employees (emp_username, emp_password, emp_firstname, emp_lastname) VALUES (?, ?, ?, ?)",
			new Object[] {register.getUsername(), register.getPassword(), register.getFirstname(), register.getLastname()},
			new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});
	}
	
	public boolean isExistingUserName(String username) {
		List<Integer> userIds = jdbcTemplate.query(
				"SELECT M.emp_id FROM employees M WHERE M.emp_username=?",
				new Object[] { username },
				new RowMapper<Integer>() {
					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getInt("emp_id");
					}
				});
		return !userIds.isEmpty();
	}

}
