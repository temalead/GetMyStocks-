package bot.service.telegram;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebhookBot extends TelegramWebhookBot {
    String botPath;
    String botUsername;
    String botToken;
    TelegramUpdateHandler handler;

    public WebhookBot(TelegramUpdateHandler handler) {
        this.handler = handler;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return handler.handleUpdate(update);
    }


}
