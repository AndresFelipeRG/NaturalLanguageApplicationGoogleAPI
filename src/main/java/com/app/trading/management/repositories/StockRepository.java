package com.app.trading.management.repositories;

import org.springframework.data.repository.CrudRepository;

import com.app.trading.management.model.Stock;

public interface StockRepository extends CrudRepository<Stock, Long>{

}
