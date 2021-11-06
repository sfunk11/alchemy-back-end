package com.revature.util;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;




public class ImageSplit {

	

	public static BufferedImage[] splitImage(String photoFileName ) throws IOException{
		 URL url = new URL("https://puzzle-alchemy-pieces.s3.us-east-2.amazonaws.com/" + photoFileName);
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

	        	return imgs;
	        
		     
		}
	

}




