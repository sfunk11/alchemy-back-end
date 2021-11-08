package com.revature.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.model.User;
import com.revature.repository.UserRepo;

@Service
public class UserService {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
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
	
	public User updateUser(User user) {
		uRepo.updateUserProfile(user.getDisplayName(), user.getF_name(), user.getL_name(), user.getRoleID(), user.getUserID());
		return user;
	}
	
	public User getUserByEmail(String email) {
		return uRepo.findByEmail(email);
	}

	
	public User getUserByUserID(int id) {
		return uRepo.findByUserID(id);
	}
	
	
	public String deleteUser(int adminId,User user) {
		
		User admin = uRepo.findByUserID(adminId);
		if(admin != null && admin.getRoleID() == 1) {
			uRepo.delete(user);
			log.info("deleteUser: User Deleted");
			return "User Deleted";

		}
		else 
			{
			log.error("deleteUser: Only the admin can delete users" );
			throw new IllegalArgumentException("Only the admin can delete users");
			}
		
	}

	
}

