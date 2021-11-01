package com.revature.util;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.service.FileStore;

public class ImageSplit {

	@Autowired
	private static FileStore fileStore;

		
//	    public static void main(String[] args) throws IOException {
//	        // Setting Chrome as an agent
//	        System.setProperty("http.agent", "Chrome");
//
////	        // reading the original image file
////	        File file = new File("/Users/jvv/Downloads/catface.jpeg");
////	        FileInputStream sourceFile = new FileInputStream(file);
////	        
//	        // reading the file from a URL
//	        URL url = new URL("https://www.educative.io/api/edpresso/shot/5120209133764608/image/5075298506244096/test.jpg");
//	        InputStream is = url.openStream();
//	        BufferedImage image = ImageIO.read(is);

	        // initalizing rows and columns
	
	public static void splitImage(String photoFileName ) throws IOException{
		 URL url = new URL("https://puzzle-alchemy-pieces.s3.us-east-2.amazonaws.com/" + photoFileName);
		 System.out.println(url);
	        InputStream is = url.openStream();
	        BufferedImage image = ImageIO.read(is);
		
		int rows = 10;
	        int columns = 1;
	        int images = rows * columns;
	        
	        // initializing array to hold subimages
	        BufferedImage imgs[] = new BufferedImage[images];

	        // Equally dividing original image into subimages
	        int subimage_Width = image.getWidth() / columns;
	        int subimage_Height = image.getHeight() / rows;
	        
	        int current_img = 0;
	        
	        // iterating over rows and columns for each sub-image
	        for (int i = 0; i < rows; i++)
	        {
	        	for (int j = 0; j < columns; j++)
	            {
	        	       // Creating sub image
	                imgs[current_img] = new BufferedImage(subimage_Width, subimage_Height, image.getType());
	                Graphics2D img_creator = imgs[current_img].createGraphics();

	                // coordinates of source image
	                int src_first_x = subimage_Width * j;
	                int src_first_y = subimage_Height * i;

	                // coordinates of sub-image
	                int dst_corner_x = subimage_Width * j + subimage_Width;
	                int dst_corner_y = subimage_Height * i + subimage_Height;
	                 
	                img_creator.drawImage(image, 0, 0, subimage_Width, subimage_Height, src_first_x, src_first_y, dst_corner_x, dst_corner_y, null);
	                current_img++;
	            }
	        }

//	        //writing sub-images into image files
//	        for (int i = 0; i < images; i++)
//	        {
//	            File outputFile = new File("img" + i + ".jpg");
//	            ImageIO.write(imgs[i], "jpg", outputFile);
//	        }
//	        System.out.println("Sub-images have been created.");
	       
//	    }
//	
		     for (int i = 0; i<images; i++) {   
		    	 Map<String, String> metadata = new HashMap<String,String>();
		    	 ByteArrayOutputStream os = new ByteArrayOutputStream();
		    	 ImageIO.write(imgs[i], "jpg", os);
		    	 InputStream piece = new ByteArrayInputStream(os.toByteArray());
	
		      
		    	
		    	 String directory = photoFileName.substring(0,photoFileName.lastIndexOf('.'));
//		    	
		    	
		    	String path = String.format("%s/%s", "puzzle-alchemy-pieces", directory);
		    	System.out.println(path);
		        String fileName = directory+ "_" + i + ".jpg";
		        System.out.println(fileName);
		        System.out.println(piece.toString());
		        fileStore.upload(path, fileName, Optional.of(metadata), piece);
		     }
		     
	}
	}




