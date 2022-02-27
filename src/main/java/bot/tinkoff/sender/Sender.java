package bot.tinkoff.sender;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface Sender {
    BotApiMethod<Message> getInfo(Message message);
}
