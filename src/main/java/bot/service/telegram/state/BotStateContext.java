package bot.service.telegram.state;

import bot.service.telegram.handlers.MessageHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
//State machine pattern
public class BotStateContext {
    private Map<BotState, MessageHandler> handlers=new HashMap<>();
}
