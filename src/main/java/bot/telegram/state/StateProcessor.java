package bot.telegram.state;

import bot.telegram.handlers.MessageHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StateProcessor {
    Map<BotState, MessageHandler> handlers = new HashMap<>();

    @Autowired
    public StateProcessor(List<MessageHandler> handlers) {
        handlers.forEach(handler -> this.handlers.put(handler.getHandlerName(), handler));
    }

    public SendMessage processMessage(BotState state, Message message) {
        MessageHandler handler = findNeededHandler(state);
        return handler.sendMessageDependsOnState(message);
    }


    public MessageHandler findNeededHandler(BotState state) {
        if (isShareHandler(state)) {
            return handlers.get(BotState.WANNA_GET_SHARE);
        }
        if (isBondHandler(state)) {
            return handlers.get(BotState.WANNA_GET_BOND);
        }
        if (isPortfolioHandler(state)) {
            return handlers.get(BotState.PORTFOLIO);
        }
        return handlers.get(BotState.HINT);
    }

    private boolean isShareHandler(BotState state) {
        switch (state) {
            case FIND_SHARE:
            case WANNA_GET_SHARE:
                return true;
        }
        return false;
    }

    private boolean isBondHandler(BotState state) {
        switch (state) {
            case WANNA_GET_BOND:
            case FIND_BOND:
                return true;
        }
        return false;
    }


    private boolean isPortfolioHandler(BotState state) {
        switch (state) {
            case PORTFOLIO:
            case DELETE_PORTFOLIO:
            case GET_PORTFOLIO:
            case MAKE_PORTFOLIO:
            case UPDATE_PORTFOLIO:
                return true;
        }
        return false;
    }

}
