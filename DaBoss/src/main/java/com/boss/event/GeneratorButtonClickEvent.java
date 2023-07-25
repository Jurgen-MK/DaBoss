package com.boss.event;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boss.service.NameGenerator;

import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import reactor.core.publisher.Mono;

@Service
public class GeneratorButtonClickEvent implements EventListener<ButtonInteractionEvent> {

	@Autowired
	NameGenerator nameGen;

	@Override
	public Class<ButtonInteractionEvent> getEventType() {
		return ButtonInteractionEvent.class;
	}

	@Override
	public Mono<Void> execute(ButtonInteractionEvent event) {
		return processCommand(event);
	}

	public Mono<Void> processCommand(ButtonInteractionEvent event) {
		return Mono.just(event).filter(eventent -> event.getCustomId().matches("orkButton|eldarButton"))				
				.flatMap(eventent -> event.reply().withEmbeds(createResponse(event.getCustomId()))).then();
	}

	public EmbedCreateSpec createResponse(String buttonId) {
		try {
			String[] nt = null;
			if (buttonId.equals("orkButton")) {
				nt = nameGen.generateOrk();
			} else if(buttonId.equals("eldarButton")) {
				nt = nameGen.generateEldar();
			}
			EmbedCreateSpec embed = EmbedCreateSpec.builder().color(Color.GREEN).addField("Имя", nt[0], false)
					.addField("Титул", nt[1], false).build();
			return embed;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EmbedCreateSpec.builder().description("Шота не работаит").build();
	}

}
