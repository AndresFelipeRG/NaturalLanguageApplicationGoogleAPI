package com.app.trading.management.db;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.QueryResults;

public interface DataStoreOperations {
	
	public QueryResults<Entity> getAllEntities(String kind);
	public JSONArray  convertEntitiestoJson(QueryResults<Entity> queryResult);
	public void uploadEntities(List<Entity> entity);
	public Entity createEntity(String kind, String keyName, Map<String, ?> properties);
	public void uploadEntity(Entity entity);
}
