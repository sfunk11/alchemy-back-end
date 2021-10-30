package com.revature.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.awt.image.BufferedImage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.revature.config.BucketName;
import com.revature.model.Photo;
import com.revature.repository.PhotoRepo;
import com.revature.repository.UserRepo;
import com.revature.util.ImageSplit;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PhotoServiceImpl implements PhotoService{
	
	private final FileStore fileStore;
	private final PhotoRepo pRepo;
	private final UserRepo uRepo;

	@Override
	public Photo savePhoto(String title, String description, MultipartFile file, String uploader) {
		 //check if the file is empty
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
       
        //get file metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        //Save Image in S3 and then save Todo in the database
        String path = String.format("%s/%s", BucketName.TODO_IMAGE.getBucketName(), "uploadedPhotos");
        String fileName = String.format("%s", file.getOriginalFilename());
        try {
            fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
        Photo photo = Photo.builder()
                .description(description)
                .title(title)
                .imagePath(path)
                .imageFileName(fileName)
                .build();
        photo.setUploader(uRepo.findByEmail(uploader));
        pRepo.save(photo);
        return pRepo.findByTitle(photo.getTitle());
    }
	@Override
	public byte[] downloadPhoto(Long id) {
		 Photo photo = pRepo.findById(id).get();
	        return fileStore.download(photo.getImagePath(), photo.getImageFileName());
	    }

	@Override
	public List<Photo> getallPhotos() {
		List<Photo> todos = new ArrayList<>();
        pRepo.findAll().forEach(todos::add);
        return todos;
    }
	
	public BufferedImage[] splitPhoto(Long id) throws IOException{
		Photo photo = pRepo.findById(id).get();
		byte[] image = fileStore.download(photo.getImagePath(), photo.getImageFileName());
		InputStream targetStream = new ByteArrayInputStream(image);
		
		BufferedImage[] imagePieces = ImageSplit.splitImage(targetStream);
		return imagePieces;
	}

}