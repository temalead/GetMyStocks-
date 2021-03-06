package bot.telegram.handler;

import bot.entity.User;
import bot.repository.UserService;
import bot.telegram.ui.keyboard.MainMenuKeyboard;
import bot.telegram.ui.buttons.BotMessageSend;
import bot.telegram.state.AvailableCommands;
import bot.telegram.state.BotCommand;
import bot.telegram.state.CommandProcessor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@RequiredArgsConstructor
@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageUpdateHandler {
    final MainMenuKeyboard menu;
    final CommandProcessor controller;
    final UserService service;

    String chatId;

    public BotApiMethod<?> handleMessage(Message message) {
        chatId= String.valueOf(message.getChatId());
        if (!message.hasText()) {
            return sendError();
        }


        BotCommand botCommand = findCommandByUserMessage(message);


        log.info("Current bot state {}", botCommand);
        return controller.processMessage(botCommand, message);
    }


    private SendMessage sendError() {
        SendMessage reply = SendMessage.builder().chatId(chatId).text(BotMessageSend.UNRECOGNIZED_MESSAGE.getMessage()).build();
        reply.enableMarkdown(true);
        reply.setReplyMarkup(menu.getKeyboard());
        return reply;
    }

    public BotCommand findCommandByUserMessage(Message message) {
        User user = service.getUserOrCreateNewUserByChatId(chatId);

        String input = message.getText();

        BotCommand botCommand = AvailableCommands.findCommand(input);


        if (user.getCommand().equals(BotCommand.DEFAULT) && botCommand == null) {
            botCommand = BotCommand.UNRECOGNIZED;
        }

        if (botCommand == null) {
            botCommand = user.getCommand();
        }


        user.setCommand(botCommand);
        service.saveCondition(user);
        return botCommand;
    }
}
