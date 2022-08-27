package com.boss.messages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.bernardomg.tabletop.dice.history.RollHistory;
import com.bernardomg.tabletop.dice.interpreter.DiceInterpreter;
import com.bernardomg.tabletop.dice.interpreter.DiceRoller;
import com.bernardomg.tabletop.dice.notation.DiceNotationExpression;
import com.bernardomg.tabletop.dice.parser.DefaultDiceParser;
import com.bernardomg.tabletop.dice.parser.DiceParser;
import com.boss.event.EventListener;
import com.boss.event.MessageListener;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.rest.util.Color;
import reactor.core.publisher.Mono;

@Service
public class MessageDiceRoll extends MessageListener implements EventListener<MessageCreateEvent> {

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
				.filter(message -> message.getContent().matches(
						Pattern.compile("[Босс, бросай кубы]+([1-9]\\d*)?d([1-9]\\d*)([/x][1-9]\\d*)?([+-]\\d+)?",
								Pattern.UNICODE_CASE).toString()))
				.flatMap(Message::getChannel).flatMap(channel -> channel.createMessage(createResponse(eventMessage)))
				.then();
	}

	public MessageCreateSpec createResponse(Message message) {
		Matcher m = Pattern.compile("([1-9]\\d*)?d([1-9]\\d*)([/x][1-9]\\d*)?([+-]\\d+)?")
				.matcher(message.getContent());
		if (m.find()) {
			DiceParser parser = new DefaultDiceParser();
			DiceNotationExpression parsed = parser.parse(m.group(0).toString());
			DiceInterpreter<RollHistory> roller = new DiceRoller();
			RollHistory rolls = roller.transform(parsed);
			return MessageCreateSpec.builder().content(rolls.toString() + " Сумма - " + rolls.getTotalRoll()).build();
		} else {
			return MessageCreateSpec.builder().content("Шота не работаит").build();
		}
	}

}
