package com.thirstteacafe.employees.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class JdbcUserDao implements UserDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String QUERY_SELECT = 
			"SELECT"
			+ "M.emp_id, "
			+ "M.emp_username, "
			+ "M.emp_firstname, "
			+ "M.emp_lastname, "
			+ "M.emp_minhours "
			+ "M.emp_maxhours "
			+ "M.emp_canlift "
			+ "M.emp_food "
			+ "M.emp_drinks "
			+ "M.emp_admin";
		
	@Override
	public List<UserData> getUserSearchResult(UserRequestData request){
		List<UserData> Users = jdbcTemplate.query(
			QUERY_SELECT + " FROM employees M WHERE LOWER(M.emp_username) LIKE LOWER(?)",
			new Object[] { "%"+request.getUsername()+"%"},
			new RowMapper<UserData>() {
				@Override
				public UserData mapRow(ResultSet rs, int rowNum) throws SQLException {
					UserData results = createUserDataFromResultSet(rs);
					return results;
				}
			}
		);
		return Users;
	}

	@Override
	public UserData getUserByID(int request){
		List<UserData> Users = jdbcTemplate.query(
			QUERY_SELECT + " FROM employees M WHERE M.emp_id=?",
			new Object[] {request},
			new RowMapper<UserData>() {
				@Override
				public UserData mapRow(ResultSet rs, int rowNum) throws SQLException {
					UserData results = createUserDataFromResultSet(rs);
					return results;
				}
			}
		);
		return Users.get(0);
	}
	
	private UserData createUserDataFromResultSet (ResultSet rs) throws SQLException {
		UserData results = new UserData();
		results.setId(rs.getInt("emp_id"));
		results.setUsername(rs.getString("emp_username"));
		results.setFirstname(rs.getString("emp_firstname"));
		results.setLastname(rs.getString("emp_lastname"));
		results.setMinHours(rs.getFloat("emp_minhours"));
		results.setMaxHours(rs.getFloat("emp_maxhours"));
		results.setCanLift(rs.getBoolean("emp_canlift"));
		results.setFood(rs.getBoolean("emp_food"));
		results.setDrinks(rs.getBoolean("emp_drinks"));
		results.setAdmin(rs.getBoolean("emp_admin"));
		return results;
	}
}
