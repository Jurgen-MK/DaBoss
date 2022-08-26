package com.boss.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WikiQuoteService {

	@Value("${warhammer-quotes-source}")
	private String basePath;

	public List<String> parse() throws IOException {
		List<String> quotes = new ArrayList<>();
		Document docroot = Jsoup.connect(basePath).get();
		Elements quotesElements = docroot.getElementsByClass("q");
		quotesElements.forEach(el -> quotes.add(el.text()));
		quotesElements = docroot.getElementsByClass("mw-parser-output");
		Elements ulElements = new Elements();
		quotesElements.forEach(el -> ulElements.addAll(el.getElementsByTag("ul")));
		Elements liElements = new Elements();
		ulElements.forEach(ulel -> liElements.addAll(ulel.select("li:not([class])")));
		liElements.forEach(liel -> quotes.add(liel.text()));
		quotes.removeAll(Collections.singleton(null));
		quotes.removeAll(Collections.singleton(""));
		return quotes;
	}
	
	public String randomQuote() throws IOException {
		Random rand = new Random();
		List<String> quotes = parse();
        return quotes.get(rand.nextInt(quotes.size()));
	}

}
