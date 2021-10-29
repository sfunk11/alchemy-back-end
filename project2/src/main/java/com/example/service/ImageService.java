package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Image;
import com.example.repo.ImageRepo;

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
		return iRepo.findByImgURL(url);
	}
	
	public Image getImageByID(int id) {
		return iRepo.findByImgID(id);
	}
	
	public void deleteImage(Image img) {
		iRepo.delete(img);
	}
	
	public void updateImage(Image img) {
		iRepo.updateImg(img);
	}

}
