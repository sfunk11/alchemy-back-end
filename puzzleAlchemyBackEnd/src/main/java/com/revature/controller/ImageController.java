package com.revature.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.service.ImageService;

@RestController
@RequestMapping(value="image")
public class ImageController {

	private ImageService iServ;
	
	public ImageController() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public ImageController(ImageService iServ) {
		super();
		this.iServ = iServ;
	}
	
	
	
}
