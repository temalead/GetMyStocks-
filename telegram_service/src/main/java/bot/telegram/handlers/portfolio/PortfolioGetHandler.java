package bot.telegram.handlers.portfolio;


import bot.entity.User;
import bot.exception.sender.NonExistentPortfolioMessage;
import bot.repository.UserService;
import bot.telegram.handlers.PortfolioMessageHandler;
import bot.telegram.keyboard.PortfolioMenuKeyBoard;
import bot.telegram.state.BotCommand;
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
    PortfolioMenuKeyBoard keyBoard;
    UserService service;

    @Override
    public SendMessage sendMessageDependsOnState(Message message) {
        User user = service.getUserOrCreateNewUserByChatId(message.getChatId().toString());
        if (user.getPortfolio()==null){
            SendMessage result = SendMessage.builder().text(NonExistentPortfolioMessage.createMessageError()).chatId(user.getId()).build();
            result.setReplyMarkup(keyBoard.getKeyboard());
            return result;
        }

        SendMessage portfolioInfo = sender.getPortfolioInfo(message,user);
        portfolioInfo.setReplyMarkup(keyBoard.getKeyboard());

        return portfolioInfo;
    }

    @Override
    public BotCommand getHandlerName() {
        return BotCommand.GET_PORTFOLIO;
    }
}
