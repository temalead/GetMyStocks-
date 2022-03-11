package bot.telegram.handlers.share;

import bot.entity.User;
import bot.repository.UserService;
import bot.telegram.handlers.MessageHandler;
import bot.telegram.buttons.BotMessageSend;
import bot.telegram.state.BotState;
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
public class ShareMessageHandler implements MessageHandler {
    UserService service;
    MessageSender sender;

    @Override
    public SendMessage sendMessageDependsOnState(Message message) {
        String chatId = message.getChatId().toString();
        User user = service.getUserOrCreateNewUserByChatId(chatId);
        BotState state = user.getState();

        SendMessage reply = null;

        if (state.equals(BotState.WANNA_GET_SHARE)) {
            user.setState(BotState.FIND_SHARE);
            reply = SendMessage.builder().text(BotMessageSend.SHARE_ADVICE_MESSAGE.getMessage()).chatId(chatId).build();
        }
        if (state.equals(BotState.FIND_SHARE)) {
            reply = sender.getShareInfo(message, user);
            if (reply.getText().startsWith("Error")) {
                return reply;
            }
            user.setState(BotState.NONE);
        }
        service.saveCondition(user);

        return reply;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.WANNA_GET_SHARE;
    }
}


