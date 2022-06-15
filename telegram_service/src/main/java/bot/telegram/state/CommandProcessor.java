package bot.telegram.state;

import bot.service.handlers.MessageHandler;
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
public class CommandProcessor implements Processor {
    Map<BotCommand, MessageHandler> handlers = new HashMap<>();

    @Autowired
    public CommandProcessor(List<MessageHandler> handlers) {
        handlers.forEach(handler -> this.handlers.put(handler.getHandlerName(), handler));
    }

    @Override
    public SendMessage processMessage(BotCommand state, Message message) {
        MessageHandler handler = findNeededHandler(state);
        return handler.sendMessageDependsOnCommand(message);
    }


    @Override
    public MessageHandler findNeededHandler(BotCommand state) {
        if (isShareHandler(state)) {
            return handlers.get(BotCommand.FIND_SHARE);
        }
        if (isBondHandler(state)) {
            return handlers.get(BotCommand.FIND_BOND);
        }
        if (isPortfolioHandler(state)) {
            return handlers.get(BotCommand.PORTFOLIO);
        }
        return handlers.get(BotCommand.HINT);
    }

    private boolean isShareHandler(BotCommand state) {
        switch (state) {
            case FOUND_SHARE:
            case FIND_SHARE:
                return true;
        }
        return false;
    }

    private boolean isBondHandler(BotCommand state) {
        switch (state) {
            case FIND_BOND:
            case FOUND_BOND:
                return true;
        }
        return false;
    }


    private boolean isPortfolioHandler(BotCommand state) {
        switch (state) {
            case PORTFOLIO:
            case DELETE_PORTFOLIO:
            case GET_PORTFOLIO:
            case WISH_MAKE_PORTFOLIO:
            case MAKE_PORTFOLIO:
            case UPDATE_PORTFOLIO:
                return true;
        }
        return false;
    }

}
