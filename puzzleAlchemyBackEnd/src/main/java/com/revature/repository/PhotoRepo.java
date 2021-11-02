package com.revature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.revature.model.Photo;

@Repository
public interface PhotoRepo extends JpaRepository<Photo, Long> {
	
	Photo findByTitle(String title);
	
	@Procedure("approvephoto")
	void approvePhoto(Long photoId, boolean isApproved);


}
