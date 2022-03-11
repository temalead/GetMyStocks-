package bot.telegram.handlers.portfolio;


import bot.telegram.handlers.PortfolioMessageHandler;
import bot.telegram.keyboard.MainMenuKeyboard;
import bot.telegram.state.BotState;
import bot.telegram.utils.MessageSender;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PortfolioGetHandler implements PortfolioMessageHandler {
    MessageSender sender;
    MainMenuKeyboard mainMenuKeyboard;

    @Override
    public SendMessage sendMessageDependsOnState(Message message) {

        SendMessage portfolioInfo = sender.getPortfolioInfo(message);
        portfolioInfo.setReplyMarkup(mainMenuKeyboard.getKeyboard());

        return portfolioInfo;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.GET_PORTFOLIO;
    }
}
