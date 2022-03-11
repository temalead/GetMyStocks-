package bot.tinkoff.sender;

import bot.entity.User;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Sender {
    SendMessage getInfo(Message message, User user);


}
