package bot.service.telegram;

import bot.domain.ShareDto;
import bot.exception.NotFoundShareException;
import bot.service.tinkoff.utils.ShareInfoSender;
import bot.service.tinkoff.ShareService;
import bot.service.tinkoff.utils.NotFoundShareMessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class TelegramUpdateHandler {
    private final ShareService service;

    public BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasMessage()){
            Message message = update.getMessage();
            return handleMessage(message);
        }
        return null;
    }

    private SendMessage handleMessage(Message message) {
        Long chatId = message.getChatId();
        String ticker = message.getText();
        try {
            ShareDto share = service.getInfo(ticker);
            String result = ShareInfoSender.createMessage(share);
            return SendMessage.builder().chatId(String.valueOf(chatId)).text(result).build();
        } catch (NotFoundShareException e) {
            return SendMessage.builder().chatId(String.valueOf(chatId)).text(NotFoundShareMessageBuilder.createMsgError(ticker)).build();
        }
    }
}
