package edu.unomaha.whatever.simplyscoresrest.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public List<UserData> getUserSearchResult(UserRequestData request){
		return userDao.getUserSearchResult(request);
	}

	public UserData getUserByID(int request) {
		return userDao.getUserByID(request);
	}
}
