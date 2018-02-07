package edu.unomaha.whatever.simplyscoresrest.users;

import java.util.List;

public interface UserDao {

	List<UserData> getUserSearchResult(UserRequestData request);
	
	UserData getUserByID(int request);
}
