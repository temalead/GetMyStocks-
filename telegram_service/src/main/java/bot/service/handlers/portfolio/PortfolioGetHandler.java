package bot.service.handlers.portfolio;


import bot.entity.Request;
import bot.entity.User;
import bot.exception.sender.Asset;
import bot.exception.sender.NonExistentPortfolioMessage;
import bot.kafka.RequestProducer;
import bot.repository.UserService;
import bot.service.handlers.PortfolioMessageHandler;
import bot.telegram.ui.keyboard.PortfolioMenuKeyBoard;
import bot.telegram.state.BotCommand;
import bot.service.MessageSender;
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

    RequestProducer producer;


    @Override
    public SendMessage sendMessageDependsOnCommand(Message message) {
        String chatId = String.valueOf(message.getChatId());
        User user = service.getUserOrCreateNewUserByChatId(chatId);
        if (user.getPortfolio() == null) {
            SendMessage result = SendMessage.builder().text(NonExistentPortfolioMessage.createMessageError()).chatId(user.getId()).build();
            result.setReplyMarkup(keyBoard.getKeyboard());
            return result;
        }
        producer.sendRequest(new Request(message.getText(), user, Asset.ABSTRACT));
        SendMessage portfolioInfo = sender.getPortfolioInfo(chatId);
        portfolioInfo.setReplyMarkup(keyBoard.getKeyboard());


        service.saveCondition(user);

        return portfolioInfo;
    }

    @Override
    public BotCommand getHandlerName() {
        return BotCommand.GET_PORTFOLIO;
    }
}
