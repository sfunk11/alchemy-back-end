package com.revature.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.revature.model.Photo;
import com.revature.service.PhotoServiceImpl;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/photos")
@AllArgsConstructor
@CrossOrigin("*")
public class PhotoController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());


	private PhotoServiceImpl service;

	
	 @GetMapping()
	    public ResponseEntity<List<Photo>> getAllPuzzles() {
		 log.info("/photos requested returns all puzzles");
	        return new ResponseEntity<>(service.getAllApprovedPuzzles(), HttpStatus.OK);
	    }
	
	 @GetMapping("/admin")
	    public ResponseEntity<List<Photo>> getAllPhotos() {
		 log.info("/admin requested returns list of all photos");
	        return new ResponseEntity<>(service.getallPhotos(), HttpStatus.OK);
	    }

	    @PostMapping(
	            path = "",
	            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
	            produces = MediaType.APPLICATION_JSON_VALUE
	    )    
	    public ResponseEntity<Photo> savePhoto(@RequestParam("title") String title,
	                                         @RequestParam("description") String description,

	                                         @RequestParam("file") MultipartFile file, @RequestParam("uploader") String name) {
	    	log.info("post requested photo saved to bucket");
	    	return new ResponseEntity<>(service.savePhoto(title, description, file, name), HttpStatus.OK);

	    }

	    @GetMapping(value = "/{id}")
	    public byte[] downloadPhoto(@PathVariable("id") Long id) {
	    	log.info("/{id} @pathvariable requested returns photo by id");
	        return service.downloadPhoto(id);
	    }
	    

	    @PostMapping(value = "/admin/approve/{adminId}/{photoId}")
	    public void approvePuzzle(@PathVariable("adminId") int adminId, @PathVariable("photoId") Long photoId) throws IOException {
	    	
	    	service.approvePhoto(adminId, photoId);
	    	log.info("post /admin/approve/{adminId}/{photoId} @pathvariable requested admin submit photo approval");
	    	
	    }
	    
	    @DeleteMapping("admin/reject/{adminId}/{photoId}")
	    public ResponseEntity<Object> deletePhoto(@PathVariable("adminId") int adminId, @PathVariable("photoId") Long photoId) {
	    	log.info("delete  admin/reject/{adminId}/{photoId} @pathvariable requested admin submit phot approval");
	    	return new ResponseEntity<Object>(service.deleteRejectedPhoto(adminId,photoId), HttpStatus.ACCEPTED);
	    }
	   
	    @PutMapping("/{id}/{email}/{makePublic}")
	    public void togglePublicAccess (@PathVariable("id")long photoId, @PathVariable("email")String email, @PathVariable("makePublic")boolean makePublic) {
	    	service.TogglePublicAccess(photoId, email, makePublic);
	    }
}

