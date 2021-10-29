package com.revature.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="images")
public class Image {
	
	@Id
	@Column(name="image_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int imgId;
	
	@Column(name="image_category", unique=true, nullable=false)
	private String imgCategory;
	
	@Column(name="image_url")
	private String imgUrl;
	
	public Image() {
		
	}
	
	public Image(String imgCategory, String imgUrl) {
		this.imgCategory = imgCategory;
		this.imgUrl = imgUrl;
	}
	
	public Image(int imgID, String imgCategory, String imgUrl) {
		this.imgCategory = imgCategory;
		this.imgUrl = imgUrl;
		this.imgId = imgID;
	}

	public int getImgID() {
		return imgId;
	}

	public void setImgID(int imgID) {
		this.imgId = imgID;
	}

	public String getImgCategory() {
		return imgCategory;
	}

	public void setImgCategory(String imgCategory) {
		this.imgCategory = imgCategory;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	@Override
	public String toString() {
		return "Image [imgID=" + imgId + ", imgCategory=" + imgCategory + ", imgUrl=" + imgUrl + "]";
	}
	
	

}
