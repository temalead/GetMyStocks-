package bot.telegram.state;

import bot.telegram.state.handlers.MessageHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Processor {
    SendMessage processMessage(BotCommand state, Message message);
    MessageHandler findNeededHandler(BotCommand state);
}
