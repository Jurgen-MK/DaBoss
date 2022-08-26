package com.boss.messages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.boss.event.EventListener;
import com.boss.event.MessageListener;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.rest.util.Color;
import reactor.core.publisher.Mono;

@Service
public class MessageWaaagh extends MessageListener implements EventListener<MessageCreateEvent> {
	
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
	    	return Mono.just(eventMessage)
	    	          .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
	    	          .filter(message -> message.getContent().matches(Pattern.compile("[W|w]+[A|a+]+[G|g]+[H|h]", Pattern.UNICODE_CASE).toString()))
	    	          .flatMap(Message::getChannel)
	    	          .flatMap(channel -> channel.createMessage(createResponse()))
	    	          .then();
	    }
	    
	    public MessageCreateSpec createResponse() {	  
	    	FileInputStream fis = null;
			try {
				fis = new FileInputStream(ResourceUtils.getFile("classpath:static/images/orkingintensifies.gif"));
			} catch (FileNotFoundException e) {				
				e.printStackTrace();
			}
	    	return MessageCreateSpec.builder()	
	    			.content("WAAАААААААААААAGH!!")
	    			.addFile("orkingintensifies.gif", fis)  			
	    			.build();    	
	    }

}
