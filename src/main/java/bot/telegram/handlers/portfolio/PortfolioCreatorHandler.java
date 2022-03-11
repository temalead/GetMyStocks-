package bot.telegram.handlers.portfolio;

import bot.entity.User;
import bot.exception.NonExistentPortfolioException;
import bot.repository.UserService;
import bot.telegram.buttons.BotMessageSend;
import bot.telegram.handlers.PortfolioMessageHandler;
import bot.telegram.keyboard.MainMenuKeyboard;
import bot.telegram.state.BotState;
import bot.telegram.utils.MessageSender;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class PortfolioCreatorHandler implements PortfolioMessageHandler {
    UserService service;
    MessageSender sender;
    MainMenuKeyboard mainMenuKeyboard;

    @Override
    public SendMessage sendMessageDependsOnState(Message message) {
        String chatId = message.getChatId().toString();
        User user = service.getUserOrCreateNewUserByChatId(chatId);
        BotState state = user.getState();
        SendMessage reply = SendMessage.builder().chatId(chatId).text(state.name()).build();


        if (state.equals(BotState.WANNA_MAKE_PORTFOLIO)){
            user.setState(BotState.MAKE_PORTFOLIO);
            reply=SendMessage.builder().chatId(chatId).text(BotMessageSend.MAKE_PORTFOLIO_ADVICE.getMessage()).build();
        }
        if (state.equals(BotState.MAKE_PORTFOLIO)){
            reply=sender.getPortfolioInfo(message, user);
            if (reply.getText().startsWith("Error")) {
                return reply;
            }
            reply.setReplyMarkup(mainMenuKeyboard.getKeyboard());
        }

        service.saveCondition(user);

        log.info("User from creator {}",user);

        return reply;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.WANNA_MAKE_PORTFOLIO;
    }
}
