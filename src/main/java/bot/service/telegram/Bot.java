package bot.service.telegram;

import bot.config.TelegramBotBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {
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
        execute(SendMessage.builder().chatId(String.valueOf(message.getChatId())).text(message.getText()).build());

    }
}
