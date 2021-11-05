package com.revature.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
	
	public List<User> findAll();
	public User findByDisplayName(String uname);
	public User findByEmail(String email);
	public List<User> findByRoleID(int roleID);
	public User findByUserID(int userID);
	

}
