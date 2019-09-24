package com.trading.mangement.trading.management.portfolio;

import java.util.List;

import com.app.trading.management.model.Stock;
import com.app.trading.management.model.StockItem;

public interface Portfolio {
	
	final String [] STOCKS = new String[]{"GS","AAPL","IBM","MSFT","NKE"};
	public String [] getStocks();
	public String toJson();
	public List<Stock> createStocks();
	public List<StockItem> createStockItems();
	public StockItem createStockItem(String stock);
	public Stock createStock(String stock);
}
