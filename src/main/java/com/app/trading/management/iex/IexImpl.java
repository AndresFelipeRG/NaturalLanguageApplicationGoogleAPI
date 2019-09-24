package com.app.trading.management.iex;

import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.app.trading.management.db.DataStoreOperations;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.QueryResults;

public class IexImpl implements Iex{
	
	@Autowired
	DataStoreOperations datastoreOperations;
	
	String token;
	private final String baseUrl = "https://cloud.iexapis.com/stable";
	private final String newsEndpoint = "/stock/symbol/news/last/10";
	public IexImpl() {}
	
	@PostConstruct
	public void init() {
		
		 QueryResults<Entity> result = datastoreOperations.getAllEntities("IEX");
		 token = result.hasNext() ?result.next().getString("token"): "";
		 System.out.println("Token: "+ token);
		 System.out.println("News: " + getNews("GS"));
		
	}
	
	public String getNews(String stock) {
		String news = "";
		String url = baseUrl+newsEndpoint.replaceAll("symbol", stock)+"?token="+token;
		System.out.println(url);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
		
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
		
		JSONArray responseJson = new JSONArray(response.getBody());
		Iterator<Object> iterator = responseJson.iterator();
		while(iterator.hasNext()) {
			JSONObject item = (JSONObject) iterator.next();
			news = news  + item.get("summary") +"\n";
		}
		return news;
	}
	

}
