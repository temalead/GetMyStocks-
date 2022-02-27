package bot.service.telegram.handlers;

import bot.domain.ShareDto;
import bot.exception.NotFoundShareException;
import bot.service.telegram.keyboard.MainMenuKeyboard;
import bot.service.telegram.state.BotState;
import bot.service.tinkoff.ShareService;
import bot.service.tinkoff.utils.NotFoundShareMessageBuilder;
import bot.service.tinkoff.utils.ShareInfoSender;
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
        Long chatId = message.getChatId();
        log.info("Message: {}",message);
        String text = message.getText();
        if (text.equals("/start")){
            return getStartMenu(String.valueOf(chatId));
        }else if (text.equals("/make_collection")){
            return SendMessage.builder().chatId(String.valueOf(chatId)).text("Not found").build();
        }
        else if(text.equals("Get bond by figi") || text.equals("Get portfolio") ){
            return getStartMenu(String.valueOf(chatId));
        }
        else {
            return sendMessage(message,chatId);
        }
    }

    private SendMessage sendMessage(Message message,Long id) {
        String chatId = String.valueOf(id);
        String ticker = message.getText();
        try {

            ShareDto share = service.getInfo(ticker);
            String result = ShareInfoSender.createMessage(share);
            return SendMessage.builder().chatId(chatId).text(result).build();
        } catch (CompletionException e) {
            return SendMessage.builder().chatId(chatId).text(NotFoundShareMessageBuilder.createMsgError(ticker)).build();
        }
    }

    private SendMessage getStartMenu(String chatId){
        SendMessage sendMessage = SendMessage.builder().chatId(chatId).text(BotState.SHOW_HELP.getMessage()).build();
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(menu.getMainMenuKeyboard());
        return sendMessage;
    }
}
