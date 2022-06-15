package bot.service.handlers;

import bot.telegram.state.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler {
    SendMessage sendMessageDependsOnCommand(Message message);
    BotCommand getHandlerName();
}
