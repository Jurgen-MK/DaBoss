package com.boss.messages;

import org.springframework.stereotype.Service;

import com.boss.event.EventListener;
import com.boss.event.MessageListener;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import reactor.core.publisher.Mono;

@Service
public class MessageHelp extends MessageListener implements EventListener<MessageCreateEvent> {

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
    	          .filter(message -> message.getContent().equalsIgnoreCase("Босс, хелп"))
    	          .flatMap(Message::getChannel)
    	          .flatMap(channel -> channel.createMessage(createResponse()))
    	          .then();
    }
    
    public EmbedCreateSpec createResponse() {
    	EmbedCreateSpec embed = EmbedCreateSpec.builder()
    			.color(Color.BLUE)
    			.title("Помащ Босса")
    			.description("**Босс, какое число?**\r\n"
    					+ "Показывает текущую дату в имперском формате\r\n"
    					+ "**Босс, цитатку**\r\n"
    					+ "Дергает рандомную вахацитату с викицитатника\r\n"
    					+ "**Босс, пастукай (указать юзера)**\r\n"
    					+ "стукает указанного юзера\r\n"
    					+ "**Босс, бросай кубы**\r\n"
    					+ "Роллит кубы (Босс, бросай кубы 3d6 - заролит 3 d6 кубов).")
    			.build();
    	return embed;
    }
    
}