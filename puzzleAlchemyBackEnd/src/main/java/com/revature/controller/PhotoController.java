package com.revature.controller;

import java.io.IOException;
import java.util.List;

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


	private PhotoServiceImpl service;

	
	 @GetMapping()
	    public ResponseEntity<List<Photo>> getAllPuzzles() {
	        return new ResponseEntity<>(service.getAllApprovedPuzzles(), HttpStatus.OK);
	    }
	
	 @GetMapping("/admin")
	    public ResponseEntity<List<Photo>> getAllPhotos() {
	        return new ResponseEntity<>(service.getallPhotos(), HttpStatus.OK);
	    }

	    @PostMapping(
	            path = "",
	            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
	            produces = MediaType.APPLICATION_JSON_VALUE
	    )    
	    public ResponseEntity<Photo> savePhoto(@RequestParam("title") String title,
	                                         @RequestParam("description") String description,
	                                         @RequestParam("file") MultipartFile file, @RequestParam("uploader") String name, @RequestParam("makePublic")boolean makePublic) {
	        return new ResponseEntity<>(service.savePhoto(title, description, file, name, makePublic), HttpStatus.OK);
	    }

	    @GetMapping(value = "/{id}")
	    public byte[] downloadPhoto(@PathVariable("id") Long id) {
	        return service.downloadPhoto(id);
	    }
	    

	    @PostMapping(value = "/admin/approve/{adminId}/{photoId}")
	    public void approvePuzzle(@PathVariable("adminId") int adminId, @PathVariable("photoId") Long photoId) throws IOException {
	    	
	    	service.approvePhoto(adminId, photoId);
	    	
	    }
	    
	    @DeleteMapping("admin/reject/{adminId}/{photoId}")
	    public ResponseEntity<Object> deletePhoto(@PathVariable("adminId") int adminId, @PathVariable("photoId") Long photoId) {
	    	return new ResponseEntity<Object>(service.deleteRejectedPhoto(adminId,photoId), HttpStatus.ACCEPTED);
	    }
	   
	    @PutMapping("/{id}/{email}/{makePublic}")
	    public void togglePublicAccess (@PathVariable("id")long photoId, @PathVariable("email")String email, @PathVariable("makePublic")boolean makePublic) {
	    	service.TogglePublicAccess(photoId, email, makePublic);
	    }
}

