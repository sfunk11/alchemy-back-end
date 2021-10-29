package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repo.UserRepo;

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
		return uRepo.findByUserName(username);
	}
	
	public List<User> getUserByRoleID(int id){
		return uRepo.findByUserRole(id);
	}
	
	public User getUserByUserID(int id) {
		return uRepo.findByUserID(id);
	}
	
	public User getUserByUsernameAndEmail(String username, String email) {
		return uRepo.findByUserNameAndEmail(username, email);
	}
	
	public void deleteUser(User user) {
		uRepo.delete(user);
	}
	
	public void updateUser(User user) {
		uRepo.updateUser(user);
	}

}
