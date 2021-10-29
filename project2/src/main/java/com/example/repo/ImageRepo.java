package com.example.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Image;

@Repository
public interface ImageRepo extends JpaRepository<Image, Integer>{

	public List<Image> findAll();
	public Image findByImgCategory(String cat);
	public Image findByImgURL(String url);
	public Image findByImgID(int imgID);
	public Image updateImg(Image img);
	
}
