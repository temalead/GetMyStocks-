package bot.telegram.handlers.bond;

import bot.telegram.handlers.MessageHandler;
import bot.telegram.state.BotState;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Component
public class BondMessageHandler implements MessageHandler {
    @Override
    public SendMessage sendMessageDependsOnState(Message message) {
        return null;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.WANNA_GET_BOND;
    }
}
