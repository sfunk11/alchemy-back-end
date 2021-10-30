package com.revature.repository;

import org.springframework.data.repository.CrudRepository;

import com.revature.model.Photo;

public interface PhotoRepo extends CrudRepository<Photo, Long> {
	
	Photo findByTitle(String title);

}
