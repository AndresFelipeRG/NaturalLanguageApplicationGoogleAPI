package com.app.trading.management.config;


import java.io.IOException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.app.trading.management.db.DataStoreOperationsImpl;
import com.app.trading.management.google.CredentialsImpl;
import com.app.trading.management.iex.IexImpl;
import com.app.trading.management.language.LanguageServiceImpl;
import com.app.trading.management.language.NaturalLanguageProcessorImpl;
import com.app.trading.management.portfolio.PortfolioImpl;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class CredentialsConfig {
	
	
	@Bean
	@Scope("singleton")
	public CredentialsImpl credentials() throws IOException {
				return new CredentialsImpl();
	}
	@Bean
	@Scope("singleton")
	public DataStoreOperationsImpl dataStoreOperationsImp () {
				return new  DataStoreOperationsImpl();
	}
	@Bean
	public LanguageServiceImpl languageServiceImpl() {
				return new LanguageServiceImpl();
	}
	@Bean
	public PortfolioImpl portfolio() {
				return new PortfolioImpl();
	}
	@Bean
	public IexImpl iex() {
				return new IexImpl();
	}
	@Bean
	public NaturalLanguageProcessorImpl newsProcessor() {
				return new NaturalLanguageProcessorImpl ();
	}
	@Bean
	public DataSource dataSource(@Value("${hana.url}") final String url,
				@Value("${hana.user}")final String user,
				@Value("${hana.password}") final String password) {
		return DataSourceBuilder.create()
			.type(HikariDataSource.class)
			.driverClassName(com.sap.db.jdbc.Driver.class.getName())
			.url(url)
			.username(user)
			.password(password)
			.build();
		
	}
		
}
