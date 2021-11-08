package com.revature.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
	
	public List<User> findAll();
	public User findByDisplayName(String uname);
	public User findByEmail(String email);
	public List<User> findByRoleID(int roleID);
	public User findByUserID(int userID);
	
	@Transactional
	@Modifying
	@Query("update User u set u.displayName = :displayName, u.f_name= :f_name, u.l_name = :l_name, u.roleID= :roleID where u.userID = :userId")
	void updateUserProfile(@Param(value = "displayName") String displayName, @Param(value="f_name") String fName, @Param(value="l_name") String lName, 
				@Param(value="roleID") int roleId, @Param(value="userId") int userId );
	

}
