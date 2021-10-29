package com.revature.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.model.Image;

@Repository
public interface ImageRepo extends JpaRepository<Image, Integer>{

	public List<Image> findAll();
	public Image findByImgCategory(String cat);
	public Image findByImgUrl(String url);
	public Image findByImgId(int imgID);
	
	
}
