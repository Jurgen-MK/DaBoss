package com.boss.messages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.boss.event.EventListener;
import com.boss.event.MessageListener;
import com.boss.service.NameGenerator;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.component.ActionRow;
import discord4j.core.object.component.Button;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.rest.util.Color;
import reactor.core.publisher.Mono;

@Service
public class MessageGenerateName extends MessageListener implements EventListener<MessageCreateEvent> {

	@Autowired
	NameGenerator nameGen;

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
				.filter(message -> message.getContent().equalsIgnoreCase("Босс, парням нужна погремуха"))
				.flatMap(Message::getChannel).flatMap(channel -> channel.createMessage(createResponse())).then();
	}

	public MessageCreateSpec createResponse() {
		MessageCreateSpec message = MessageCreateSpec.builder()
				.addComponent(ActionRow.of(Button.primary("eldarButton", "Феешки")))
				.addComponent(ActionRow.of(Button.primary("orkButton", "Бойзы"))).content("Чья пагримуха нужна")
				.build();
		return message;
	}
}
