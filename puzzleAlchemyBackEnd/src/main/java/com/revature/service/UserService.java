package com.revature.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.model.User;
import com.revature.repository.UserRepo;

@Service
public class UserService {
	
	private UserRepo uRepo;
	
	public UserService() {
		
	}
	
	@Autowired
	public UserService(UserRepo uRepo) {
		super();
		this.uRepo = uRepo;
	}
	
	public List<User> getAllUsers(){
		return uRepo.findAll();
	}
	
	public void insertUser(User user) {
		uRepo.save(user);
	}
	
	public User getUserByUsername(String username) {
		return uRepo.findByuName(username);
	}
	
	public User getUserByEmail(String email) {
		return uRepo.findByEmail(email);
	}
	
	public List<User> getUserByRoleID(int id){
		return uRepo.findByRoleID(id);
	}
	
	public User getUserByUserID(int id) {
		return uRepo.findByUserID(id);
	}
	
	public User getUserByUsernameAndEmail(String username, String email) {
		return uRepo.findByuNameAndEmail(username, email);
	}
	
	public void deleteUser(User user) {
		uRepo.delete(user);
	}
	
	public void updateUser(User user) {
		uRepo.save(user);
	}

}

