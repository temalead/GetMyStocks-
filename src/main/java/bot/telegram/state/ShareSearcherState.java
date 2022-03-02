package bot.telegram.state;

import bot.telegram.handlers.MessageHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class ShareSearcherState implements MessageHandler {

    @Override
    public SendMessage sendMessageDependsOnState(Message message) {
        return null;
    }
}
