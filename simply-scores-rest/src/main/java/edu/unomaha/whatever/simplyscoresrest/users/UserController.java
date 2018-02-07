package edu.unomaha.whatever.simplyscoresrest.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/userSearch", method=RequestMethod.POST)
	public List<UserData> getUsers(@RequestBody UserRequestData request){
		return userService.getUserSearchResult(request);
	}
	
	@RequestMapping(value="/user/{userID}", method=RequestMethod.GET)
	public UserData getUserByID(@PathVariable Integer userID){
		return userService.getUserByID(userID);
	}
}
