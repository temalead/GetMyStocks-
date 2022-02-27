package bot.telegram.handlers;

import bot.domain.ShareDto;
import bot.telegram.keyboard.ButtonMenuEnum;
import bot.telegram.keyboard.MainMenuKeyboard;
import bot.telegram.state.BotMessageSend;
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


    public BotApiMethod<?> handleMessage(Message message) {
        String chatId = String.valueOf(message.getChatId());
        log.info("Message: {}", message);
        String text = message.getText();
        ButtonMenuEnum value = ButtonMenuEnum.valueOf(text);
        if (text.equals("/start")){
            return getStartMenu(chatId);
        }
        switch (value) {
            case HELP:
                return helpMessage(chatId);
            case GET_LIST:
                return null;
            case GET_BOND:
                return sendMessage(message);
            case GET_SHARE:
                return sendMessage(message);
            case UPDATE_LIST:
                return null;
            case SET_LIST:
                return null;
            default:
                return sendError(chatId);
        }
    }

    private SendMessage helpMessage(String chatId) {
        SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(BotMessageSend.HELP_MESSAGE.getMessage()).build();
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(menu.getMainMenuKeyboard());
        return sendMessage;
    }



    private SendMessage sendMessage(Message message) {
        String chatId = String.valueOf(message.getChatId());
        String ticker = message.getText();
        try {

            ShareDto share = service.getInfo(ticker);
            String result = ShareInfoSender.createMessage(share);
            return SendMessage.builder().chatId(chatId).text(result).build();
        } catch (CompletionException e) {
            return SendMessage.builder().chatId(chatId).text(NotFoundShareMessageBuilder.createMsgError(ticker)).build();
        }
    }

    private SendMessage getStartMenu(String chatId) {
        SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(BotMessageSend.HELP_MESSAGE.getMessage()).build();
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(menu.getMainMenuKeyboard());
        return sendMessage;
    }


    private SendMessage sendError(String chatId) {
        SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(BotMessageSend.NOT_RECOGNIZE.getMessage()).build();
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(menu.getMainMenuKeyboard());
        return sendMessage;
    }
}
