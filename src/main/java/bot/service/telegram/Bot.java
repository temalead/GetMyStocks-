package bot.service.telegram;

import bot.domain.ShareDto;
import bot.exception.NotFoundShareException;
import bot.service.telegram.config.TelegramBotConfig;
import bot.service.telegram.utils.ShareInfoSender;
import bot.service.tinkoff.ShareService;
import bot.service.tinkoff.utils.NotFoundShareMessageBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.CompletionException;

@Component
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {
    private final ShareService service;
    private final TelegramBotConfig config;

    @Override
    public String getBotUsername() {
        return config.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            handleMessage(update.getMessage());
        }
    }

    private void handleMessage(Message message) throws TelegramApiException {

        Long chatId = message.getChatId();
        String ticker = message.getText();
        try {
            ShareDto info = service.getInfo(ticker);
            String result = ShareInfoSender.createMessage(info);
            execute(SendMessage.builder().chatId(String.valueOf(chatId)).text(result).build());
        } catch (CompletionException e) {
            execute(SendMessage.builder().chatId(String.valueOf(chatId)).text(NotFoundShareMessageBuilder.createMsgError(ticker)).build());
        }

    }
}