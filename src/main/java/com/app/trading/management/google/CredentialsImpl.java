package com.app.trading.management.google;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


import com.google.auth.oauth2.GoogleCredentials;

@Component
public class CredentialsImpl implements Credentials {
	
	
	@Value("classpath:GOOGLE/credentials.json")
	Resource credentials;
	
	
	GoogleCredentials myCredentials;
	
	public CredentialsImpl() throws IOException {

	}
	@PostConstruct
	private void init() throws IOException {
		
		System.out.println("Creating credentials");
		this.myCredentials = GoogleCredentials.fromStream(credentials.getInputStream());
		System.out.println("Credentials created");
		System.out.println(myCredentials.toString());
		System.out.println(credentials.toString());
	}
	@Override
	public GoogleCredentials getMyCredentials() {
		return this.myCredentials;
	}
			

}
