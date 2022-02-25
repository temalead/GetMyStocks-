package bot.service.telegram.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
@Service
@Slf4j
public class UpdateHandler {
    private final MessageUpdateHandler messageHandler;
    private final CallbackQueryUpdateHandler callbackHandler;

    public BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            return callbackHandler.handleCallbackQuery(update.getCallbackQuery());
        } else {
            Message message = update.getMessage();
            log.info("It`s a message");
            return SendMessage.builder().chatId(String.valueOf(message.getChatId())).text("Text").build();
        }
    }
}
