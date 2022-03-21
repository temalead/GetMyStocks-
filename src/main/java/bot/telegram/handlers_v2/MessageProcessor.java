package bot.telegram.handlers_v2;

import bot.telegram.state.BotState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageProcessor {
    SendMessage sendMessageDependsOnState(String message);
    BotState getHandlerName();
}
