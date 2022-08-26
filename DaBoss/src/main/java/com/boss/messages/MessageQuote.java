package com.boss.messages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.boss.event.EventListener;
import com.boss.event.MessageListener;
import com.boss.service.WikiQuoteService;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.rest.util.Color;
import reactor.core.publisher.Mono;

@Service
public class MessageQuote extends MessageListener implements EventListener<MessageCreateEvent> {

	@Autowired
	WikiQuoteService wqp;

	@Override
	public Class<MessageCreateEvent> getEventType() {
		return MessageCreateEvent.class;
	}

	@Override
	public Mono<Void> execute(MessageCreateEvent event) {
		return processCommand(event.getMessage());
	}

	@Override
	public Mono<Void> processCommand(Message eventMessage) {
		return Mono.just(eventMessage).filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
				.filter(message -> message.getContent().equalsIgnoreCase("Босс, цитатку")).flatMap(Message::getChannel)
				.flatMap(channel -> channel.createMessage(createResponse())).then();
	}

	public MessageCreateSpec createResponse() {
		String quote = "";
		try {
			quote = wqp.randomQuote();
		} catch (IOException e) {
			e.printStackTrace();
		}
		EmbedCreateSpec embed = EmbedCreateSpec.builder().color(Color.GREEN).description("**"+quote+"**")
				.image("attachment://quote-ork.png")
				.build();
		try {
			return MessageCreateSpec.builder()			
			.addFile("quote-ork.png", new FileInputStream(ResourceUtils.getFile("classpath:static/images/quote-ork.png")))
			.addEmbed(embed)
			.build();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return MessageCreateSpec.builder().content("**"+quote+"**").build();		 
	}

}
