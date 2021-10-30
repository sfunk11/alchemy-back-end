package com.revature.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Photo {

	  @Id
	    @GeneratedValue
	    private Long id;
	    private String title;
	    private String description;
	    private String imagePath;
	    private String imageFileName;
	    @ManyToOne
	    @JoinColumn(name = "uploader", nullable=false )
	    private User uploader;
	    private String category;
	
}
