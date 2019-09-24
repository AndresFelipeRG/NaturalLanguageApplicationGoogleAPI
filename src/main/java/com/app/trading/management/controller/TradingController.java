package com.app.trading.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.trading.management.iex.Iex;
import com.trading.mangement.trading.management.portfolio.Portfolio;

@Controller
public class TradingController {
	
	@Autowired
	Portfolio portfolio;
	@Autowired
	Iex iex;
	
	@RequestMapping(method = RequestMethod.GET, value="/getStocks")
	public ResponseEntity<String> getStocks(){
		return new ResponseEntity<String>(portfolio.toJson(),HttpStatus.OK);
	}

}
