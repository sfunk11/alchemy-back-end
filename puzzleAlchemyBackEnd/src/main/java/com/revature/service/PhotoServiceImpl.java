package com.revature.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.revature.model.Photo;
import com.revature.model.User;
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
	private UserService uServ;
	

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

        String path = String.format("%s", "puzzle-alchemy-pieces");

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
		List<Photo> photos = new ArrayList<>();
        pRepo.findAll().forEach(photos::add);
        
        return photos;
    }
	
	public List<Photo> getAllApprovedPuzzles(){
		List<Photo> photos = new ArrayList<>();
		pRepo.findAll().forEach(photos::add);
		for(Photo photo:photos) {
			if (photo.isApproved() == false){
				photos.remove(photo);
			}
		}
		return photos;
	}
	

	public void splitPhoto(Long id) throws IOException{
		Photo photo = pRepo.findById(id).get();
		System.out.println(photo.getTitle());
		 BufferedImage[] images =ImageSplit.splitImage(photo.getImageFileName());
		  for (int i = 0; i<10; i++) {   
		    	 Map<String, String> metadata = new HashMap<String,String>();
		    	 ByteArrayOutputStream os = new ByteArrayOutputStream();
		    	 ImageIO.write(images[i], "jpg", os);
		    	 InputStream piece = new ByteArrayInputStream(os.toByteArray());
	
		      
		    	
		    	 String directory = photo.getImageFileName().substring(0,photo.getImageFileName().lastIndexOf('.'));	    	
		    	
		    	String path = String.format("%s/%s", "puzzle-alchemy-pieces", directory);
		        String fileName = directory+ "_" + i + ".jpg";
		        System.out.println(piece.toString());
		        fileStore.upload(path, fileName, Optional.of(metadata), piece);
		     }
	
	}

	public void approvePhoto(int userId, Long photoId) throws IOException {
		
		 User currentUser = uServ.getUserByUserID(userId);
		 if (currentUser.getRoleID() == 1) {
			 pRepo.approvePhoto(photoId, true);
			 splitPhoto(photoId);

		 }else throw new IllegalArgumentException("Only the admin can approve photos");
		
	}
	
	public String deleteRejectedPhoto(int userId, Long photoId) {
		User currentUser = uServ.getUserByUserID(userId);
		Photo photo = pRepo.getById(photoId);
		if(currentUser.getRoleID() == 1) {
			pRepo.deleteById(photoId);
			fileStore.deletePhoto(photo.getImagePath(), photo.getImageFileName());
			return "Photo deleted.";
		}else throw new IllegalArgumentException("Only the admin can reject photos.");
	} 

}
