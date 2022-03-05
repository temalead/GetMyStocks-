package bot.telegram.handlers.bond;

import bot.domain.User;
import bot.telegram.handlers.MessageHandler;
import bot.telegram.model.BotMessageSend;
import bot.telegram.state.BotState;
import bot.repository.UserService;
import bot.telegram.utils.MessageSender;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Component
public class BondMessageHandler implements MessageHandler {
    UserService service;
    MessageSender sender;


    @Override
    public SendMessage sendMessageDependsOnState(Message message) {
        User user = service.getUserOrCreateNewUserByChatId(message.getChatId().toString());
        BotState state = user.getState();

        SendMessage reply = null;

        String chatId = message.getChatId().toString();
        if (state.equals(BotState.WANNA_GET_BOND)) {
            user.setState(BotState.FIND_BOND);
            reply = SendMessage.builder().text(BotMessageSend.BOND_ADVICE_MESSAGE.getMessage()).chatId(chatId).build();
        }
        if (state.equals(BotState.FIND_BOND)) {
            reply = sender.getBondInfo(message);
            if (reply.getText().startsWith("Error")){
                return reply;
            }

            user.setState(BotState.NONE);
        }
        service.saveCondition(user);

        return reply;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.WANNA_GET_BOND;
    }
}
