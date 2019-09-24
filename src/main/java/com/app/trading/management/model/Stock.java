package com.app.trading.management.model;

import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Stock {
	
	@Id
	String ObjectID;
	String Name;
	String Attribute1;
	String Attribute2;
	String UnitOfMeasure;
	Float UnitNumber;

}
