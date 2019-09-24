package com.app.trading.management.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.trading.maangement.repositories.StockRepository;
import com.app.trading.management.model.Stock;

public class HanaOperationsImpl implements HanaOperations {
	
	@Autowired
	StockRepository repository;
	
	public List<Stock> findAllStocks(){
		List<Stock> stocks = new ArrayList<>();
		repository.findAll().forEach(stocks::add);
		return stocks;
		
	}
	
	public boolean insertStock(Stock stock) {
		try {
			repository.save(stock);
			return true;
		}
		catch(Exception exception) {
			return false;
		}
	}

}
