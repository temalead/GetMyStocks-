package bot.service;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageSender {
    void send(Message message);
}
