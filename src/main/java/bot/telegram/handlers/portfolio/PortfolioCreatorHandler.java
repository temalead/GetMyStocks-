package bot.telegram.handlers.portfolio;

import bot.domain.User;
import bot.repository.UserService;
import bot.telegram.buttons.BotMessageSend;
import bot.telegram.handlers.MessageHandler;
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
public class PortfolioCreatorHandler implements MessageHandler {
    UserService service;
    MessageSender sender;
    MainMenuKeyboard mainMenuKeyboard;

    @Override
    public SendMessage sendMessageDependsOnState(Message message) {
        String chatId = message.getChatId().toString();
        User user = service.getUserOrCreateNewUserByChatId(chatId);
        BotState state = user.getState();
        SendMessage reply = null;

        if (state.equals(BotState.MAKE_PORTFOLIO)){
            user.setState(BotState.WAIT_MAKE_PORTFOLIO);
            reply=SendMessage.builder().chatId(chatId).text(BotMessageSend.MAKE_PORTFOLIO_ADVICE.getMessage()).build();
        }
        if (state.equals(BotState.WAIT_MAKE_PORTFOLIO)){
            reply=sender.getCreatedPortfolio(message);
            if (reply.getText().startsWith("Error")) {
                return reply;
            }
            user.setState(BotState.NONE);
            reply.setReplyMarkup(mainMenuKeyboard.getKeyboard());
        }
        return reply;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.MAKE_PORTFOLIO;
    }
}
