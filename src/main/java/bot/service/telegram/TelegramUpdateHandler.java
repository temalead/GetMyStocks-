package bot.service.telegram;

import bot.exception.NotFoundShareException;
import bot.service.telegram.utils.ShareInfoSender;
import bot.service.tinkoff.ShareService;
import bot.service.tinkoff.utils.NotFoundShareMessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.math.BigDecimal;

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
        String text = message.getText();
        try {
            BigDecimal dividend = service.getLastDividendByTicker(text);
            String result = ShareInfoSender.createMessage(text, dividend);
            return SendMessage.builder().chatId(String.valueOf(chatId)).text(result).build();
        } catch (NotFoundShareException e) {
            return SendMessage.builder().chatId(String.valueOf(chatId)).text(NotFoundShareMessageBuilder.createMsgError(text)).build();
        }
    }
}
