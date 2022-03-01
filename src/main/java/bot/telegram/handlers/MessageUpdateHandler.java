package bot.telegram.handlers;

import bot.domain.User;
import bot.telegram.keyboard.MainMenuKeyboard;
import bot.telegram.state.BotMessageSendHinter;
import bot.telegram.state.BotState;
import bot.telegram.state.StateController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@RequiredArgsConstructor
@Service
@Slf4j
public class MessageUpdateHandler {
    private final MainMenuKeyboard menu;
    private final StateController controller;
    private final User user;


    public BotApiMethod<?> handleMessage(Message message) {

        BotState botState;
        String chatId = String.valueOf(message.getChatId());

        if (!message.hasText()) {
            return sendError(chatId);
        }
        String input = message.getText();
        switch (input) {
            case "/start":
                botState = BotState.SHOW_START_MENU;
                break;
            case "Get portfolio":
            case "Update portfolio":
            case "Get bond by figi":
            case "Make portfolio":
                botState = BotState.UNRECOGNIZED;
                break;
            case "Get share by ticker":
                botState = BotState.WANNA_GET_SHARE;
                break;
            case "Help me!":
                botState = BotState.SHOW_HELP_MENU;
                break;
            default:
                botState=user.getState();
        }
        user.setState(botState);
        return controller.processMessage(user.getState(), message);
    }


    private SendMessage sendError(String chatId) {
        SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(BotMessageSendHinter.UNRECOGNIZED.getMessage()).build();
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(menu.getMainMenuKeyboard());
        return sendMessage;
    }
}
