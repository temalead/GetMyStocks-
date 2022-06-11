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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageUpdateHandler {
    MainMenuKeyboard menu;
    CommandProcessor controller;
    UserService service;


    public BotApiMethod<?> handleMessage(Message message) {


        String chatId = String.valueOf(message.getChatId());
        User user = service.getUserOrCreateNewUserByChatId(chatId);

        String input = message.getText();

        BotCommand botCommand = AvailableCommands.findCommand(input);

        if (botCommand == null) {
            botCommand = user.getCommand();
        }
        System.out.println(botCommand);


        if (user.getCommand().equals(BotCommand.DEFAULT) && botCommand==null) {
            botCommand = BotCommand.UNRECOGNIZED;
        }

        if (!message.hasText()) {
            return sendError(chatId);
        }

        user.setCommand(botCommand);

        service.saveCondition(user);
        log.info("Current bot state {}", user.getCommand());
        return controller.processMessage(botCommand, message);
    }


    private SendMessage sendError(String chatId) {
        SendMessage reply = SendMessage.builder().chatId(chatId).text(BotMessageSend.UNRECOGNIZED_MESSAGE.getMessage()).build();
        reply.enableMarkdown(true);
        reply.setReplyMarkup(menu.getKeyboard());
        return reply;
    }
}
