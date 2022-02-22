package bot.service.telegram;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class TelegramUpdateHandler {
    public BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasMessage()){
            Message message = update.getMessage();
            return handleMessage(message);
        }
        return null;
    }

    private BotApiMethod<?> handleMessage(Message message) {
        return null;
    }
}
