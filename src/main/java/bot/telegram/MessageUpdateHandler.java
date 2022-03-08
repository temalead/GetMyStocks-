package bot.telegram;

import bot.domain.User;
import bot.repository.UserService;
import bot.telegram.keyboard.MainMenuKeyboard;
import bot.telegram.buttons.BotMessageSend;
import bot.telegram.state.BotState;
import bot.telegram.state.StateProcessor;
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
    StateProcessor controller;
    UserService service;

    public BotApiMethod<?> handleMessage(Message message) {

        String chatId = String.valueOf(message.getChatId());
        User user = service.getUserOrCreateNewUserByChatId(chatId);
        BotState botState;


        if (!message.hasText()) {
            return sendError(chatId);
        }
        String input = message.getText();
        switch (input) {
            case "/start":
                botState = BotState.GET_START_MENU;
                break;
            case "My portfolio":
                botState = BotState.PORTFOLIO;
                break;
            case "Get share by ticker":
                botState = BotState.WANNA_GET_SHARE;
                break;
            case "Get bond by name":
                botState=BotState.WANNA_GET_BOND;
                break;
            case "Help me!":
                botState = BotState.GET_HELP;
                break;
            case "Get portfolio":
                botState=BotState.GET_PORTFOLIO;
                break;
            case "Back":
                botState=BotState.BACK;
                break;
            case "Make portfolio":
                botState=BotState.WANNA_MAKE_PORTFOLIO;
                break;
            case "Delete portfolio":
                botState=BotState.DELETE_PORTFOLIO;
                break;
            case "Update portfolio":
                botState=BotState.UPDATE_PORTFOLIO;
                break;
            default:
                if (user.getState().equals(BotState.NONE)) {
                    botState = BotState.UNRECOGNIZED;
                } else {
                    botState = user.getState();
                }
        }
        user.setState(botState);

        service.saveCondition(user);
        log.info("Current bot state {}", botState.name());
        return controller.processMessage(user.getState(), message);
    }


    private SendMessage sendError(String chatId) {
        SendMessage reply = SendMessage.builder().chatId(chatId).text(BotMessageSend.UNRECOGNIZED_MESSAGE.getMessage()).build();
        reply.enableMarkdown(true);
        reply.setReplyMarkup(menu.getKeyboard());
        return reply;
    }
}
