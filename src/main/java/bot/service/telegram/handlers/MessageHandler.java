package bot.service.telegram.handlers;

import bot.service.telegram.state.BotState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler {
    SendMessage handleMessage(Message message);

    BotState getCurrentState();
}
