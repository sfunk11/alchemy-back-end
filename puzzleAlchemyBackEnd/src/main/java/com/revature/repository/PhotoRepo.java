package com.revature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.model.Photo;

@Repository
public interface PhotoRepo extends JpaRepository<Photo, Long> {
	
	Photo findByTitle(String title);
	
	@Procedure("approvephoto")
	void approvePhoto(Long photoId, boolean isApproved);
	
	@Transactional
	@Modifying
	@Query("update Photo p set p.makePublic = :makePublic where p.id = :photoId")
	void togglePublicAccess(@Param(value="makePublic")boolean makePublic, @Param(value="photoId")long photoId);
	
	void deleteById(Long photoId);


}
