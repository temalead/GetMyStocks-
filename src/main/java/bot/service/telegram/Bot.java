package bot.service.telegram;

import bot.config.TelegramBotBuilder;
import bot.exception.NotFoundShareException;
import bot.service.telegram.utils.ShareInfoSender;
import bot.service.tinkoff.NotFoundShareMessageBuilder;
import bot.service.tinkoff.StockService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {
    private final StockService service;
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
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()){
            handleMessage(update.getMessage());
        }
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
}
