package bot.telegram.handlers;

import bot.domain.ShareDto;
import bot.telegram.keyboard.MainMenuKeyboard;
import bot.telegram.state.BotMessageSendHinter;
import bot.telegram.state.BotState;
import bot.telegram.state.StateController;
import bot.tinkoff.ShareService;
import bot.tinkoff.utils.NotFoundShareMessageBuilder;
import bot.tinkoff.utils.ShareInfoSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.concurrent.CompletionException;

@RequiredArgsConstructor
@Service
@Slf4j
public class MessageUpdateHandler {
    private final ShareService service;
    private final MainMenuKeyboard menu;
    private final StateController controller;


    public BotApiMethod<?> handleMessage(Message message) {
        BotState botState = null;
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
                botState = BotState.SEARCH_SHARE;
                break;
            case "Help me!":
                botState = BotState.SHOW_HELP_MENU;
            default:
                botState=BotState.UNRECOGNIZED;
        }
        return controller.processMessage(botState, message);
    }

    private SendMessage helpMessage(String chatId) {
        SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(BotMessageSendHinter.HELP_MESSAGE.getMessage()).build();
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(menu.getMainMenuKeyboard());
        return sendMessage;
    }


    private SendMessage startMenu(String chatId) {
        SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(BotMessageSendHinter.HELP_MESSAGE.getMessage()).build();
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(menu.getMainMenuKeyboard());
        return sendMessage;
    }


    private SendMessage sendError(String chatId) {
        SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(BotMessageSendHinter.NOT_RECOGNIZE.getMessage()).build();
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(menu.getMainMenuKeyboard());
        return sendMessage;
    }
}
