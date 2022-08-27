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
public class MessagePastuk extends MessageListener implements EventListener<MessageCreateEvent> {

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
				.filter(message -> message.getContent()
						.matches(Pattern.compile("[Босс, пастукай].+", Pattern.UNICODE_CASE).toString()))
				.filter(message -> !message.getUserMentions().isEmpty()).flatMap(Message::getChannel)
				.flatMap(channel -> channel.createMessage(createResponse(eventMessage))).then();
	}

	public MessageCreateSpec createResponse(Message message) {
		if (message.getUserMentions().size() > 1) {
			return MessageCreateSpec.builder().content("Давай по одному").build();
		} else {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(ResourceUtils.getFile("classpath:static/images/pastuk1.gif"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return MessageCreateSpec.builder().content("Палучай грязный сквигалюб " + message.getUserMentions().get(0).getMention())
					.addFile("pastuk1.gif", fis).build();
		}
	}

}
