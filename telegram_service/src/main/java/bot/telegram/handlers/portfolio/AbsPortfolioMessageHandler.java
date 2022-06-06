package bot.telegram.handlers.portfolio;

import bot.entity.User;
import bot.repository.UserService;
import bot.telegram.buttons.BotMessageSend;
import bot.telegram.handlers.MessageHandler;
import bot.telegram.keyboard.PortfolioMenuKeyBoard;
import bot.telegram.state.BotCommand;
import bot.telegram.state.PortfolioCommandProcessor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Component
@Slf4j
public class AbsPortfolioMessageHandler implements MessageHandler {
    UserService service;
    PortfolioMenuKeyBoard keyBoard;
    PortfolioCommandProcessor processor;


    @Override
    public SendMessage sendMessageDependsOnState(Message message) {
        String chatId = message.getChatId().toString();
        User user = service.getUserOrCreateNewUserByChatId(chatId);
        BotCommand state = user.getState();

        SendMessage reply = SendMessage.builder().chatId(message.getChatId().toString()).text(BotMessageSend.PORTFOLIO_ADVICE.getMessage()).build();
        reply.setReplyMarkup(keyBoard.getKeyboard());

        if (state.equals(BotCommand.PORTFOLIO)){
            return reply;
        }


        return processor.processMessage(state,message);
    }

    @Override
    public BotCommand getHandlerName() {
        return BotCommand.PORTFOLIO;
    }
}
