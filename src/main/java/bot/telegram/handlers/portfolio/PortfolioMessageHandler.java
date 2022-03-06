package bot.telegram.handlers.portfolio;

import bot.telegram.buttons.BotMessageSend;
import bot.telegram.handlers.MessageHandler;
import bot.telegram.keyboard.PortfolioMenuKeyBoard;
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
public class PortfolioMessageHandler implements MessageHandler {
    PortfolioMenuKeyBoard keyBoard;

    @Override
    public SendMessage sendMessageDependsOnState(Message message) {
        SendMessage result = SendMessage.builder().chatId(message.getChatId().toString()).text(BotMessageSend.PORTFOLIO_ADVICE.getMessage()).build();
        result.setReplyMarkup(keyBoard.getKeyboard());
        return result;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.PORTFOLIO;
    }
}
