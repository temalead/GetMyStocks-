package bot.telegram.state;

import bot.telegram.handlers.MessageHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Processor {
    SendMessage processMessage(BotState state, Message message);
    MessageHandler findNeededHandler(BotState state);
}
