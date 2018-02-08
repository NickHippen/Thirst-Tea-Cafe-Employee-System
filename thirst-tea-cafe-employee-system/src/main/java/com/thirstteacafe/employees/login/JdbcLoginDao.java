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
		List<Integer> userIds = jdbcTemplate.query("SELECT M.USER_ID FROM members M WHERE M.USER_NAME=? AND M.PASSWORD=?",
			new Object[] { login.getUserName(), login.getPassword() },
			new RowMapper<Integer>() {
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getInt("USER_ID");
				}
			});
		if (userIds.isEmpty()) {
			throw new AuthenticationException("Invalid login credentials");
		}
	}

	@Override
	public void register(RegisterData register) {
		jdbcTemplate.update("INSERT INTO members (USER_NAME,PASSWORD,REGION,USER_CREATE_TIME) VALUES (?, ?, ?, SYSDATE())",
				new Object[] { register.getUserName(), register.getPassword(), "Midwest" },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR });
	}
	
	public boolean isExistingUserName(String userName) {
		List<Integer> userIds = jdbcTemplate.query("SELECT M.USER_ID FROM members M WHERE M.USER_NAME=?",
				new Object[] { userName },
				new RowMapper<Integer>() {
					@Override
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getInt("USER_ID");
					}
				});
		return !userIds.isEmpty();
	}

}
