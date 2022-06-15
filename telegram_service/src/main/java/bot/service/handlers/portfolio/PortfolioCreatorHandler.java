package bot.service.handlers.portfolio;

import bot.entity.Request;
import bot.entity.User;
import bot.exception.sender.Asset;
import bot.kafka.RequestProducer;
import bot.repository.UserService;
import bot.service.handlers.PortfolioMessageHandler;
import bot.telegram.ui.buttons.BotMessageSend;
import bot.telegram.ui.keyboard.MainMenuKeyboard;
import bot.telegram.state.BotCommand;
import bot.service.MessageSender;
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
    RequestProducer producer;

    @Override
    public SendMessage sendMessageDependsOnCommand(Message message) {
        String chatId = message.getChatId().toString();
        User user = service.getUserOrCreateNewUserByChatId(chatId);
        BotCommand state = user.getCommand();
        SendMessage reply = SendMessage.builder().chatId(chatId).text(state.name()).build();


        if (state.equals(BotCommand.WISH_MAKE_PORTFOLIO)) {
            user.setCommand(BotCommand.MAKE_PORTFOLIO);
            reply = SendMessage.builder().chatId(chatId).text(BotMessageSend.MAKE_PORTFOLIO_ADVICE.getMessage()).build();
        }
        if (state.equals(BotCommand.MAKE_PORTFOLIO)) {
            producer.sendRequest(new Request(message.getText(), user, Asset.ABSTRACT));
            reply = sender.getPortfolioInfo(chatId,user);
            if (reply.getText().startsWith("Error")) {
                return reply;
            }
            reply.setReplyMarkup(mainMenuKeyboard.getKeyboard());
        }

        service.saveCondition(user);

        log.info("User from creator {}", user);

        return reply;
    }

    @Override
    public BotCommand getHandlerName() {
        return BotCommand.WISH_MAKE_PORTFOLIO;
    }
}
