package com.revature.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {
   
	ClassLoader cl = getClass().getClassLoader();
	InputStream is;
	Properties p= new Properties();
    
	public AmazonConfig() {
		is = cl.getResourceAsStream("aws.properties");
		try {
			p.load(is);
		}catch(IOException e) {
			
			
		}
	}
    
    @Bean
    public AmazonS3 s3() {
    	String key = p.getProperty("AWS_ACCESS_KEY_ID");
    	String secret = p.getProperty("AWS_SECRET_ACCESS_KEY");
        AWSCredentials awsCredentials =
                new BasicAWSCredentials(key, secret);
        return AmazonS3ClientBuilder
                .standard()
                .withRegion("us-east-2")
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

    }
}
