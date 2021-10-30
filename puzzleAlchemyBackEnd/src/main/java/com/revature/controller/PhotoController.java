package com.revature.controller;

import java.awt.image.BufferedImage;
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
import com.revature.service.PhotoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/photo")
@AllArgsConstructor
@CrossOrigin("*")
public class PhotoController {

	private PhotoService service;
	
	 @GetMapping
	    public ResponseEntity<List<Photo>> getPhotos() {
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
	    
	    @GetMapping(value="/pieces/{id}")
	    public ResponseEntity<BufferedImage[]> createPieces(@RequestParam("id") Long id) throws IOException{
	    	return new ResponseEntity<BufferedImage[]>(service.splitPhoto(id), HttpStatus.OK);
	    }
}
