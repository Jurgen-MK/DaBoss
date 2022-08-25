package com.boss.messages;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.boss.event.EventListener;
import com.boss.event.MessageListener;
import com.boss.service.ImperialDateConverter;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import reactor.core.publisher.Mono;

@Service
public class MessageImperialDate extends MessageListener implements EventListener<MessageCreateEvent>{
	
	@Autowired
	ImperialDateConverter idc;

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
    	          .filter(message -> message.getContent().equalsIgnoreCase("Босс, какое число?"))
    	          .flatMap(Message::getChannel)
    	          .flatMap(channel -> channel.createMessage(createResponse()))
    	          .then();
    }
    
    public String createResponse() {
    	return idc.currentImperialDate();
    }

}
