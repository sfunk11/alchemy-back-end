package com.revature.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.model.User;
import com.revature.service.UserService;

@RestController
@RequestMapping(value="/user")
public class UserController {
	
	private UserService uServ;
	
	public UserController() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired 
	public UserController(UserService uServ){
		super();
		this.uServ = uServ;
	}
	
	@GetMapping("/initial")
	public ResponseEntity<List<User>> insertInitialValues(){
		List<User> uList = new ArrayList<User>(Arrays.asList(new User("SFunk", "borley1@gmail.com", "Sam", "Funk", 1), new User("CVoid", "CVoid@gmail.com", "Cass", "Void", 0), 
				new User("MNieves", "MNieves@gmail.com", "Michael", "Nieves", 0), new User("SHarrington", "SHarrington@gmail.com", "Shawn", "Harrington", 0), 
				new User("JVeliz", "JVigil@gmail.com", "Joe", "Veliz", 0)));
		for(User user: uList) {
			uServ.insertUser(user);
		}
		return new ResponseEntity<List<User>>(uServ.getAllUsers(), HttpStatus.CREATED);
	}

	@GetMapping()
	public ResponseEntity<List<User>> getAllUsers(){
		return new ResponseEntity<List<User>>(uServ.getAllUsers(), HttpStatus.OK);
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable("username") String uName){
		User user = uServ.getUserByUsername(uName);
		if(user == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@DeleteMapping("/{username}")
	public ResponseEntity<String> deleteUser(@PathVariable("username") String uName){
		User user = uServ.getUserByUsername(uName);
		if(user == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		uServ.deleteUser(user);
		return new ResponseEntity<String>(uName + " was deleted.", HttpStatus.ACCEPTED);
	}
	
	@PostMapping()
	public ResponseEntity<Object> insertUser(@RequestBody User user){
		System.out.println(user);
		if(uServ.getUserByUsername(user.getuName()) != null) {
			return new ResponseEntity<>(user.getuName() + " already exists!", HttpStatus.FORBIDDEN);
		}
		uServ.insertUser(user);
		return new ResponseEntity<>(uServ.getUserByUsername(user.getuName()), HttpStatus.CREATED);
	}

}
