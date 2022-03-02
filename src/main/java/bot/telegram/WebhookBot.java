package bot.telegram;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebhookBot extends TelegramWebhookBot {
    String botPath;
    String botUsername;
    String botToken;
    UpdateHandler handler;

    public WebhookBot(UpdateHandler handler) {
        this.handler = handler;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return handler.handleUpdate(update);
    }
}
