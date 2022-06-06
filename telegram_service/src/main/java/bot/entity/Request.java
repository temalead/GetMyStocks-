package bot.entity;

import lombok.Value;
import org.telegram.telegrambots.meta.api.objects.Message;

@Value
public class Request {
    Message message;
    User user;
}
