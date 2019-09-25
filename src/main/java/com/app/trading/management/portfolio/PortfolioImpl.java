package com.app.trading.management.portfolio;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.trading.management.db.DataStoreOperations;
import com.app.trading.management.iex.Iex;
import com.app.trading.management.language.NaturalLanguageProcessor;
import com.app.trading.management.model.Stock;
import com.app.trading.management.model.StockItem;
import com.google.cloud.datastore.Entity;

@Component
public class PortfolioImpl implements Portfolio {
	
	@Autowired
	DataStoreOperations dataStoreOperations;
	
	@Autowired
	NaturalLanguageProcessor newsProcessor;
	
	@Autowired
	Iex iex;
	
	public PortfolioImpl() {
		
	}
	
	public String [] getStocks() {
		return Portfolio.STOCKS;
	}

	public String toJson() {
		return dataStoreOperations != null ? dataStoreOperations
											 .convertEntitiestoJson(dataStoreOperations.getAllEntities("Stock"))
											 .toString(): null;
	}
	public List<Stock> createStocks(){
		List<Stock> stocks = new ArrayList<>();
		for(String stock: Portfolio.STOCKS) {
			Stock stockObj = createStock(stock);
			stocks.add(stockObj);
		}
		return stocks;
	}
	public List<StockItem> createStockItems(){
		List<StockItem> items = new ArrayList<>();
		for(String stock: Portfolio.STOCKS) {
			StockItem stockItem = createStockItem(stock);
			items.add(stockItem);
		}
		return items;
	}
	public StockItem createStockItem(String stock) {
		String news = iex.getNews(stock);
		float score = newsProcessor.scoreNews(news);
		
		Date date = new Date();
		String attribute = score >  1.0 ? "buy" : "sell";
		StockItem stockItem = StockItem.builder()
								.Attribute(attribute)
								.LineItemID(stock+" "+date.toString())
								.Name(stock)
								.UnitNumber(score)
								.ObjectID(stock)
								.UnitOfMeasure("Score")
								.build();
		Map<String, Object> properties = new HashMap<>();
		properties.put("Attribute", stockItem.getAttribute());
		properties.put("LineItemID", stockItem.getLineItemID());
		properties.put("ObjectID", stockItem.getObjectID());
		properties.put("UnitNumber", stockItem.getUnitNumber());
		properties.put("UnitOfMeasure", stockItem.getUnitOfMeasure());
		properties.put("Name", stockItem.getName());
		Entity entity = dataStoreOperations.createEntity("StockItem", stock+" "+date.toString(), properties);
		dataStoreOperations.uploadEntity(entity);
		return stockItem;
	}
	@Override
	public Stock createStock(String stock) {
		String news = iex.getNews(stock);
		float score = newsProcessor.scoreNews(news);
		
		String attribute = score >  1.0 ? "buy" : "sell";
		Stock stockObject = Stock.builder()
								.Attribute1(attribute)
								.Attribute2("Attribute 2")
								.Name(stock)
								.ObjectID(stock)
								.UnitNumber(score)
								.UnitOfMeasure("Score")
								.build();
		Map<String, Object> properties = new HashMap<>();
		properties.put("Attribute1", stockObject.getAttribute1());
		properties.put("Attribute2", stockObject.getAttribute2());
		properties.put("Name", stockObject.getName());
		properties.put("ObjectID",stock);
		properties.put("UnitNumber", stockObject.getUnitNumber() );
		properties.put("UnitOfMeasure",  stockObject.getUnitOfMeasure());
		Entity entity = dataStoreOperations.createEntity("Stock", stock , properties);
		dataStoreOperations.uploadEntity(entity);				
		return stockObject;
	}
	//Update score news of portfolio every hour
	@Scheduled(fixedRate = 60000*60)
	public List<Stock> getNews() {
		return createStocks();
	}
}
