package bot.service.telegram;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Getter
@Setter
public class WebhookBot extends SpringWebhookBot {
    private String botPath;
    private String botUsername;
    private String botToken;
    private SetWebhook setWebhook;

    private TelegramUpdateHandler handler;

    public WebhookBot(SetWebhook setWebhook, TelegramUpdateHandler handler) {
        super(setWebhook);
        this.handler=handler;
    }

    public WebhookBot(DefaultBotOptions options, SetWebhook setWebhook,TelegramUpdateHandler handler) {
        super(options, setWebhook);
        this.handler=handler;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return handler.handleUpdate(update);
    }
}
