package bot.domain;

import bot.telegram.state.BotState;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Component
public class User {
    String chatId;
    BotState state;
}
