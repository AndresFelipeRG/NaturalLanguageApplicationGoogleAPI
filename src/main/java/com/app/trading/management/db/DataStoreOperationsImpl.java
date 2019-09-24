package com.app.trading.management.db;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.trading.management.google.Credentials;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Entity.Builder;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.LongValue;
//import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Value;
import com.google.protobuf.FloatValue;


public class DataStoreOperationsImpl implements DataStoreOperations{
	
	private Datastore datastore;
	private KeyFactory keyFactory;
	
	@Autowired
	Credentials credentials;
	
	public DataStoreOperationsImpl() {
		
	}
	@PostConstruct
	public void init() {
		datastore = DatastoreOptions.newBuilder().setCredentials(credentials.getMyCredentials()).build().getService(); 
		keyFactory = datastore.newKeyFactory(); 
		
		System.out.println(convertEntitiestoJson(getAllEntities("Stock")).toString());
	}
	@Override
	public QueryResults<Entity> getAllEntities(String kind) {
		
		Query<Entity> query = Query.newEntityQueryBuilder()
								   .setKind(kind)
								   .build();
	
		return datastore != null ? datastore.run(query) : null;
	}
	
	public JSONArray convertEntitiestoJson(QueryResults<Entity> queryResult ) {
		
		JSONArray  entities = new JSONArray();
		while(queryResult.hasNext()) {
			Entity entity = queryResult.next();
			Map<String, Value<?>> properties = entity.getProperties();
			Map<String, Object> entityMap = new HashMap<>();
			for(Entry <String , ? > entry: properties.entrySet()) {
				
				if(entry.getValue().getClass() == StringValue.class) {
						entityMap.put(entry.getKey(),  entity.getString(entry.getKey()));
				}
				else if(entry.getValue().getClass() == LongValue.class) {
						entityMap.put(entry.getKey(), entity.getLong(entry.getKey()));
				}
			}
			
			JSONObject entityJson = new JSONObject(entityMap);
			entities.put(entityJson);
		}
		return entities;
	}
	
	public Entity createEntity(String kind, String keyName, Map<String, ?> properties) {
		keyFactory.setKind(kind);
		Key key = keyFactory.newKey(keyName);
		Builder entityBuilder = Entity.newBuilder(key);
		
		for(String k: properties.keySet()) {
			if(properties.get(k) instanceof String) {
				String val = (String) properties.get(k);
				entityBuilder.set(k, val);
			}
			else if(properties.get(k) instanceof Float) {
				Float val = (Float) properties.get(k);
				entityBuilder.set(k, val);
			}
			
		}
		return entityBuilder.build();
	}
	

	
	@Override
	public void uploadEntities(List<Entity> entities) {
		for(Entity entity: entities) {
			datastore.put(entity);
		}
	}
	
	public void uploadEntity(Entity entity) {
		
		datastore.put(entity);
		
	}
	

}
