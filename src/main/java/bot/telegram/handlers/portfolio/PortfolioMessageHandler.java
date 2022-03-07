package bot.telegram.handlers.portfolio;

import bot.domain.User;
import bot.telegram.buttons.BotMessageSend;
import bot.telegram.handlers.MessageHandler;
import bot.telegram.keyboard.MainMenuKeyboard;
import bot.telegram.keyboard.PortfolioMenuKeyBoard;
import bot.telegram.state.BotState;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.repository.UserService;


@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Component
public class PortfolioMessageHandler implements MessageHandler {
    PortfolioMenuKeyBoard keyBoard;
    MainMenuKeyboard mainMenuKeyboard;
    UserService service;

    @Override
    public SendMessage sendMessageDependsOnState(Message message) {
        User user = service.getUserOrCreateNewUserByChatId(message.getChatId().toString());
        BotState state = user.getState();

        SendMessage reply = SendMessage.builder().chatId(message.getChatId().toString()).text(BotMessageSend.PORTFOLIO_ADVICE.getMessage()).build();
        reply.setReplyMarkup(keyBoard.getKeyboard());
        String chatId = message.getChatId().toString();


        if (state.equals(BotState.BACK)) {
            user.setState(BotState.GET_START_MENU);
            reply.setReplyMarkup(mainMenuKeyboard.getKeyboard());
        }



        return reply;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.PORTFOLIO;
    }
}
