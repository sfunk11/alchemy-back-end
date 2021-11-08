package com.revature.service;

import java.awt.image.BufferedImage;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.revature.model.Photo;
import com.revature.model.User;
import com.revature.repository.PhotoRepo;
import com.revature.repository.UserRepo;
import com.revature.util.ImageSplit;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class PhotoServiceImpl implements PhotoService{
	
//	Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final FileStore fileStore;
	private final PhotoRepo pRepo;
	private final UserRepo uRepo;
	private UserService uServ;
	

	@Override
	public Photo savePhoto(String title, String description, MultipartFile file, String uploader, boolean makePublic) {
		 //check if the file is empty
        if (file.isEmpty()) {
        	log.error("savePhoto passed a empty file");
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
            log.info("savePhoto calls FileStore upload method");
        } catch (IOException e) {
        	log.error("savePhoto failed to upload image");
            throw new IllegalStateException("savePhoto call to FileStore method failed", e);
        }
        Photo photo = Photo.builder()
                .description(description)
                .title(title)
                .imagePath(path)
                .imageFileName(fileName)
                .build();
        photo.setUploader(uRepo.findByEmail(uploader));
        photo.setMakePublic(makePublic);
        pRepo.save(photo);
        Photo newPHoto = pRepo.findByTitle(photo.getTitle());
        try {
			splitPhoto(newPHoto.getId());
			   log.info("savePhoto calls static method splitPhoto");
		} catch (IOException e) {
			log.error("savePhoto calls static method splitPhoto failed");
			e.printStackTrace();
		}
        log.info("savePhoto returns sliced file");
        return newPHoto;
       
    }
	@Override
	public byte[] downloadPhoto(Long id) {
		 Photo photo = pRepo.findById(id).get();
		 log.info("downloadPhoto returns photo by id");
	        return fileStore.download(photo.getImagePath(), photo.getImageFileName());
	    }

	@Override
	public List<Photo> getallPhotos() {
		System.out.println("in service");
		List<Photo> photos = new ArrayList<>();
        pRepo.findAll().forEach(photos::add);
        log.info("getAllPhotos returns all photos");
        return photos;
    }
	
	public List<Photo> getAllApprovedPuzzles(){
		List<Photo> photos = new ArrayList<>();
		log.info("getAllApprovedPuzzles makes request to UserRepo findAllUsers for List of Users");
		pRepo.findAll().forEach(photos::add);

		
		for(Photo photo:photos) {
			if (photo.isApproved() == false){
				log.info("getAllApprovedPuzzles removes not approved photos");
				photos.remove(photo);
			}
		}
		log.info("getAllApprovedPuzzles returns list of approved photos");

		return photos;
	}
	

	public void splitPhoto(Long id) throws IOException{
		Photo photo = pRepo.findById(id).get();
		System.out.println(photo.getTitle());
		log.info("splitPhoto calls ImageSplit Util to slice image");
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
		        log.info("splitPhoto calls FileStore upload method");
		     }
	
	}
	public void TogglePublicAccess(long photoId, String email, boolean makePublic) {
		Photo photo = pRepo.getById(photoId);
		System.out.println(photo.getUploader().getEmail());
		System.out.println(email);
		if (photo.getUploader().getEmail().trim().equals(email.trim())) {
			pRepo.togglePublicAccess( makePublic, photoId);
		} else throw new IllegalArgumentException("Only the owner can make photos public"); 
		
		
	}
	
	
	public void approvePhoto(int userId, Long photoId) throws IOException {
		
		 User currentUser = uServ.getUserByUserID(userId);
		 if (currentUser.getRoleID() == 1) {
			 pRepo.approvePhoto(photoId, true);
			 log.info("approvedPhoto : admin ID: "+ userId + "approved photo id: " + photoId);
		 }
		 else 
			 {
			 log.error("approvedPhoto : Only the admin can approve photos ");
			 throw new IllegalArgumentException("Only the admin can approve photos");
			 }
		
	}
	
	public String deleteRejectedPhoto(int userId, Long photoId) {
		User currentUser = uServ.getUserByUserID(userId);
		log.info("deleteRejectedPhoto calls UserService method getUserByUserID");
		Photo photo = pRepo.getById(photoId);
		log.info("deleteRejectedPhoto calls UserRepo method getById");
		if(currentUser.getRoleID() == 1) {
			pRepo.deleteById(photoId);
			log.info("deleteRejectedPhoto calls UserRepo method deleteId("+ photoId+")");
			fileStore.deletePhoto(photo.getImagePath(), photo.getImageFileName());
			log.info("deleteRejectedPhoto calls FileStore method deletePhoto()");
			return "Photo deleted.";
		}
		else
			{
			 log.error("deleteRejectedPhoto: Only the admin can reject photos");
			throw new IllegalArgumentException("Only the admin can reject photos.");
			}
	} 

}
