package com.boss.service;



import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@Service
public class LexicanumService {

	
	//https://wh40k.lexicanum.com/mediawiki/api.php?action=opensearch&search=Horus Heresy&limit=1&format=json поиск
	//https://wh40k.lexicanum.com/mediawiki/api.php?action=query&format=json&prop=revisions&titles="Horus Heresy"&formatversion=2&rvprop=content&rvsection=0 запрос контента
	
	public void searchArticle(String article) {
		Flux<String> lexFlux = WebClient.create().get()
				.uri("https://wh40k.lexicanum.com/mediawiki/api.php?action=query&format=json&prop=revisions&titles="
						+ article + "&formatversion=2&rvprop=content&rvsection=0")
				.retrieve().bodyToFlux(String.class);
		// String searchedLink = lexFlux.blockFirst();
		//lexFlux.subscribe(lf -> parseArticle(lf.toString()));
	}

	

}
