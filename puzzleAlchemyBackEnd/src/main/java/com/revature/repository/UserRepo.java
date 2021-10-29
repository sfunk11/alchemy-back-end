package com.revature.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
	
	public List<User> findAll();
	public User findByuName(String uname);
	public List<User> findByRoleID(int roleID);
	public User findByuNameAndEmail(String uname, String email);
	public User findByUserID(int userID);
	

}
