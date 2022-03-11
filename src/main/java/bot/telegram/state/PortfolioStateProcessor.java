package bot.telegram.state;


import bot.telegram.handlers.MessageHandler;
import bot.telegram.handlers.PortfolioMessageHandler;
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
public class PortfolioStateProcessor implements Processor {
    Map<BotState, PortfolioMessageHandler> handlers = new HashMap<>();

    @Autowired
    public PortfolioStateProcessor(List<PortfolioMessageHandler> handlers) {
        handlers.forEach(handler -> this.handlers.put(handler.getHandlerName(), handler));
    }


    @Override
    public SendMessage processMessage(BotState state, Message message) {
        MessageHandler handler = findNeededHandler(state);
        return handler.sendMessageDependsOnState(message);
    }

    public PortfolioMessageHandler findNeededHandler(BotState state) {
        if (isCreatedHandler(state)) {
            return handlers.get(BotState.WANNA_MAKE_PORTFOLIO);
        }
        if (state.equals(BotState.GET_PORTFOLIO)){
            return handlers.get(BotState.GET_PORTFOLIO);
        }
        return null;
    }

    private boolean isCreatedHandler(BotState state) {
        switch (state) {
            case WANNA_MAKE_PORTFOLIO:
            case MAKE_PORTFOLIO:
                return true;
        }
        return false;
    }


}
