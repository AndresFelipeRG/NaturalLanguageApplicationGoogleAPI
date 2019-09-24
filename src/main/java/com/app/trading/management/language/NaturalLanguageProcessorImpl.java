package com.app.trading.management.language;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.Sentiment;


public class NaturalLanguageProcessorImpl implements NaturalLanguageProcessor {

	@Autowired
	LanguageService language;
	
	public NaturalLanguageProcessorImpl() {
		
	}
	
	public float scoreNews(String news) {
		Document document = Document.newBuilder()
									.setContent(news)
									.setType(Type.PLAIN_TEXT)
									.build();
		Sentiment sentiment = language.getLanguage().analyzeSentiment(document).getDocumentSentiment();
	    
		return sentiment.getMagnitude() > 0 ? sentiment.getScore()*sentiment.getMagnitude():sentiment.getScore() ;
	}
}
