package com.revature.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.model.Image;
import com.revature.repository.ImageRepo;


@Service
public class ImageService {
	
	private ImageRepo iRepo;
	
	public ImageService() {
		
	}
	
	@Autowired
	public ImageService(ImageRepo iRepo) {
		super();
		this.iRepo = iRepo;
	}
	
	public List<Image> getAllImages(){
		return iRepo.findAll();
	}
	
	public void insertImage(Image img) {
		iRepo.save(img);
	}
	
	public Image getImageByCategory(String cat) {
		return iRepo.findByImgCategory(cat);
	}
	
	public Image getImageByURL(String url) {
		return iRepo.findByImgUrl(url);
	}
	
	public Image getImageByID(int id) {
		return iRepo.findByImgId(id);
	}
	
	public void deleteImage(Image img) {
		iRepo.delete(img);
	}
	
	public void updateImage(Image img) {
		iRepo.save(img);
	}

}
