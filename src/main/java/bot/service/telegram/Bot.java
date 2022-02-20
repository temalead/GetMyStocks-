package bot.service.telegram;

import bot.config.TelegramBotBuilder;
import bot.exception.NotFoundShareException;
import bot.service.telegram.utils.ShareInfoSender;
import bot.service.tinkoff.NotFoundShareMessageBuilder;
import bot.service.tinkoff.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class Bot extends TelegramWebhookBot {
    private final ShareService service;
    private final TelegramBotBuilder builder;


    @Override
    public String getBotUsername() {
        return builder.getName();
    }
    @Override
    public String getBotToken() {
        return builder.getToken();
    }
    @Override
    public String getBotPath() {
        return builder.getPath();
    }



    private void handleMessage(Message message) throws TelegramApiException {

        Long chatId = message.getChatId();
        String text = message.getText();
        try {
            BigDecimal dividend = service.getLastDividendByTicker(text);
            String result = ShareInfoSender.createMessage(text, dividend);
            execute(SendMessage.builder().chatId(String.valueOf(chatId)).text(result).build());
        } catch (NotFoundShareException e) {
            execute(SendMessage.builder().chatId(String.valueOf(chatId)).text(NotFoundShareMessageBuilder.createMsgError(text)).build());
        }

    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }


}
