package com.revature.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.revature.model.Photo;
import com.revature.service.PhotoServiceImpl;
import com.revature.service.PhotoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/photo")
@AllArgsConstructor
@CrossOrigin("*")
public class PhotoController {


	private PhotoServiceImpl service;

	
	 @GetMapping()
	    public ResponseEntity<List<Photo>> getAllPuzzles() {
	        return new ResponseEntity<>(service.getAllApprovedPuzzles(), HttpStatus.OK);
	    }
	
	 @GetMapping("/admin/all")
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
	                                         @RequestParam("file") MultipartFile file, @RequestParam("uploader") String name) {
	        return new ResponseEntity<>(service.savePhoto(title, description, file, name), HttpStatus.OK);
	    }

	    @GetMapping(value = "/{id}")
	    public byte[] downloadPhoto(@PathVariable("id") Long id) {
	        return service.downloadPhoto(id);
	    }
	    

	    @GetMapping(value = "/admin")
	    public void approvePuzzle(@RequestParam("adminId") int adminId, @RequestParam("photoId") Long photoId) throws IOException {
	    	
	    	service.approvePhoto(adminId, photoId);
	    	
	    }
	   
}

