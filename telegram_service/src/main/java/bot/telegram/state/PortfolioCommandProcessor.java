package bot.telegram.state;


import bot.service.handlers.MessageHandler;
import bot.service.handlers.PortfolioMessageHandler;
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
public class PortfolioCommandProcessor implements Processor {
    Map<BotCommand, PortfolioMessageHandler> handlers = new HashMap<>();

    @Autowired
    public PortfolioCommandProcessor(List<PortfolioMessageHandler> handlers) {
        handlers.forEach(handler -> this.handlers.put(handler.getHandlerName(), handler));
    }


    @Override
    public SendMessage processMessage(BotCommand state, Message message) {
        MessageHandler handler = findNeededHandler(state);
        return handler.sendMessageDependsOnCommand(message);
    }

    public PortfolioMessageHandler findNeededHandler(BotCommand state) {
        if (isCreatedHandler(state)) {
            return handlers.get(BotCommand.WISH_MAKE_PORTFOLIO);
        }
        if (state.equals(BotCommand.GET_PORTFOLIO)){
            return handlers.get(BotCommand.GET_PORTFOLIO);
        }
        return null;
    }

    private boolean isCreatedHandler(BotCommand state) {
        switch (state) {
            case WISH_MAKE_PORTFOLIO:
            case MAKE_PORTFOLIO:
                return true;
        }
        return false;
    }


}
