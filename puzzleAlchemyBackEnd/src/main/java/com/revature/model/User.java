package com.revature.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userID;
	
	@Column(name="user_uname")
	private String uName;
	
	@Column(name="user_email", unique=true, nullable=false)
	private String email;
	
	@Column(name="user_fname")
	private String fname;
	
	@Column(name="user_lname")
	private String lname;
	
	@Column(name="user_role")
	private int roleID;
	
	public User() {
		
	}

	public User(int userID, String uName, String email, String fname, String lname, int roleID) {
		super();
		this.userID = userID;
		this.uName = uName;
		this.email = email;
		this.fname = fname;
		this.lname = lname;
		this.roleID = roleID;
	}

	public User(String uName, String email, String fname, String lname, int roleID) {
		super();
		this.uName = uName;
		this.email = email;
		this.fname = fname;
		this.lname = lname;
		this.roleID = roleID;
	}

	public User(String uName, String password) {
		super();
		this.uName = uName;
		this.email = email;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", uName=" + uName + ", email=" + email + ", fname=" + fname
				+ ", lname=" + lname + ", roleID=" + roleID + "]";
	}
	
	

}
