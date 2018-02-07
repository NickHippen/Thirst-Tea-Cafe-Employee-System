package edu.unomaha.whatever.simplyscoresrest.users;

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
	
	@Override
	public List<UserData> getUserSearchResult(UserRequestData request){
		List<UserData> Users = jdbcTemplate.query("SELECT M.USER_ID, M.USER_NAME, M.REGION, M.USER_CREATE_TIME FROM members M WHERE LOWER(M.USER_NAME) LIKE LOWER(?)",
			new Object[] { "%"+request.getUserName()+"%"},
			new RowMapper<UserData>() {
				@Override
				public UserData mapRow(ResultSet rs, int rowNum) throws SQLException {
					UserData results = new UserData();
					results.setUserId(rs.getInt("USER_ID"));
					results.setUserName(rs.getString("USER_NAME"));
					results.setRegion(rs.getString("REGION"));
					results.setCreateDate(rs.getString("USER_CREATE_TIME"));
					
					return results;
				}
			}
		);
		return Users;
	}
	
	@Override
	public UserData getUserByID(int request){
		List<UserData> Users = jdbcTemplate.query("SELECT M.USER_ID, M.USER_NAME, M.REGION, M.USER_CREATE_TIME FROM members M WHERE M.USER_ID=?",
			new Object[] {request},
			new RowMapper<UserData>() {
				@Override
				public UserData mapRow(ResultSet rs, int rowNum) throws SQLException {
					UserData results = new UserData();
					results.setUserId(rs.getInt("USER_ID"));
					results.setUserName(rs.getString("USER_NAME"));
					results.setRegion(rs.getString("REGION"));
					results.setCreateDate(rs.getString("USER_CREATE_TIME"));
					
					return results;
				}
			}
		);
		return Users.get(0);
	}
}
