package com.revature.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.Application;
import com.revature.model.User;
import com.revature.repository.UserRepo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)


public class UserRepoTesting {

	@Autowired 
	private UserRepo uRepo;
	@AfterEach
	public void setUp() {
		User user = uRepo.findByEmail("test@test.com");
		if (user != null) {
			uRepo.delete(user);
		}
		
		
	}
	@Test
	public void saveUserTestSuccess() {
		User user = new User("test", "test@test.com", "Test", "Test", 0);
		uRepo.save(user);
		User foundUser = uRepo.findByUserID(user.getUserID());
		
		assertNotNull(foundUser);
		assertEquals(user.getEmail(), foundUser.getEmail());
	}
	
	@Test
	public void FindByEmailSuccess() {
		User user = new User("test", "test@test.com", "Test", "Test", 0);
		uRepo.save(user);
		
		User foundUser = uRepo.findByEmail("test@test.com");
		assertNotNull(foundUser);
		assertEquals(user.getEmail(), foundUser.getEmail());
	}
	
}
