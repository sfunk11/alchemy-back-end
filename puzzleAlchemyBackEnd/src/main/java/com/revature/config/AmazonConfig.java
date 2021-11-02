package com.revature.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

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
    	
        return AmazonS3ClientBuilder
                .standard()
                .withRegion("us-east-2")
                .withCredentials(new AWSCredentialsProviderChain(
                		new InstanceProfileCredentialsProvider(),
                		new ProfileCredentialsProvider()))
                .build();

    }
}
