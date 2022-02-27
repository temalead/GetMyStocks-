package bot.service.telegram.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class CallbackQueryUpdateHandler {

    public BotApiMethod<?> handleCallbackQuery(CallbackQuery callbackQuery) {
        return null;
    }
}
