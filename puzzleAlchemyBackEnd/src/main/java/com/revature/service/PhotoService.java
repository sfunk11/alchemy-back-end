package com.revature.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.revature.model.Photo;

public interface PhotoService {

	Photo savePhoto (String title, String description, MultipartFile file, String uploader);
	byte[] downloadPhoto (Long id);
	List<Photo> getallPhotos();
	public void splitPhoto(Long id)throws IOException;
	
}
