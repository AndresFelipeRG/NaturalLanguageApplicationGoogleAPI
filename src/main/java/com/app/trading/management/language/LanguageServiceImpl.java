package com.app.trading.management.language;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.trading.management.google.Credentials;
import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.services.language.v1.CloudNaturalLanguageScopes;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.LanguageServiceSettings;

public class LanguageServiceImpl implements LanguageService{

	@Autowired
	Credentials credentials;
	
	LanguageServiceClient language;

	public LanguageServiceImpl () {
		
	}

	public LanguageServiceClient getLanguage() {
		return language;
	}
	
	@PostConstruct
	public void init() throws IOException {
		
		String scopeCP = CloudNaturalLanguageScopes.CLOUD_PLATFORM;
		String scopeNL = CloudNaturalLanguageScopes.CLOUD_LANGUAGE;
		List<String> scopes = Arrays.asList(scopeCP, scopeNL);
		credentials.getMyCredentials().createScoped(scopes);
		GoogleCredentials _credentials = credentials.getMyCredentials().createScoped(scopes);
		CredentialsProvider credentialsProvider = FixedCredentialsProvider.create(_credentials);
		LanguageServiceSettings settings = LanguageServiceSettings.newBuilder().setCredentialsProvider(credentialsProvider).build();
		language = LanguageServiceClient.create(settings);
		
	     
	}
	
}
