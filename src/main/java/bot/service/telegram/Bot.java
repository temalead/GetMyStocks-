package bot.service.telegram;

import bot.config.TelegramBotBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

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
    public void onUpdateReceived(Update update) {

    }
}
