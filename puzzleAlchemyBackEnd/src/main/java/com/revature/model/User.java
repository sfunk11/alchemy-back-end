package com.revature.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Data @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode  
public class User {
	
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userID;
	
	@Column(name="user_uname")
	private String displayName;
	

	@Column(name="user_email", unique=true, nullable=false)

	private String email;
	
	@Column(name="user_fname")
	private String f_name;
	
	@Column(name="user_lname")
	private String l_name;
	
	@Column(name="user_role")
	private int roleID;
	
//	@OneToMany(mappedBy="uploader")
//	private List<Photo> photos;

	public User(String displayName, String email, String f_name, String l_name, int roleID) {
		super();
		this.displayName = displayName;
		this.email = email;
		this.f_name = f_name;
		this.l_name = l_name;
		this.roleID = roleID;
	
	}
	
	
	
	
	

}
