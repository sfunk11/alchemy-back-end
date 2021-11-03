package com.revature.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.model.User;
import com.revature.repository.UserRepo;
import com.revature.service.UserService;

public class UserServiceTesting {

	
	@Mock
	private UserRepo uRepo;
	
	private UserService uServ;
	private List<User> uList;
	
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp()throws Exception{
		 uList = new ArrayList<User>(Arrays.asList(new User(1,"SFunk", "borley1@gmail.com", "Sam", "Funk", 1), new User(2,"CVoid", "demonspawndaemnyxea@gmail.com", "Cass", "Void", 0), 
					
					new User(3,"MNieves", "MNieves@gmail.com", "Michael", "Nieves", 0), new User(4,"SHarrington", "SHarrington@gmail.com", "Shawn", "Harrington", 0), 
					new User(5,"JVeliz", "JVigil@gmail.com", "Joe", "Veliz", 0)));
		
		MockitoAnnotations.initMocks(this);
		
		when(uRepo.findAll()).thenReturn(uList);
		when(uRepo.findByUserID(1)).thenReturn(uList.get(0));
		when(uRepo.findByUserID(4)).thenReturn(uList.get(3));
		when(uRepo.findByEmail("JVigil@gmail.com")).thenReturn(uList.get(4));
	}

	@Test
	public void getUserListTest() {
		assertEquals(uList, uServ.getAllUsers());
	}
	
	@Test
	public void getSingleUserByIdTest() {
		User user = uServ.getUserByUserID(1);
		assertEquals(uList.get(0), user);
	}
	
	@Test
	public void getSingleUserByEmailTest() {
		User user = uServ.getUserByEmail("JVigil@gmail.com");
		assertEquals(uList.get(4), user);
	}

	@Test
	public void deleteUserSuccessTest() {
		assertEquals("User Deleted", uServ.deleteUser(1, uList.get(3)));
	}
	
	
	@Test
	public void deleteUserFailTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			uServ.deleteUser(3, uList.get(3));
		});
	}
	
}
