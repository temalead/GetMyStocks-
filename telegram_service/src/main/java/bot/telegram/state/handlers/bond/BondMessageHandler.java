package bot.telegram.state.handlers.bond;

import bot.entity.User;
import bot.telegram.state.handlers.MessageHandler;
import bot.telegram.ui.buttons.BotMessageSend;
import bot.repository.UserService;
import bot.telegram.state.BotCommand;
import bot.service.MessageSender;
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
    public SendMessage sendMessageDependsOnCommand(Message message) {
        String chatId = message.getChatId().toString();
        User user = service.getUserOrCreateNewUserByChatId(chatId);
        BotCommand state = user.getCommand();

        SendMessage reply = null;

        if (state.equals(BotCommand.FIND_BOND)) {
            user.setCommand(BotCommand.FOUND_BOND);
            reply = SendMessage.builder().text(BotMessageSend.BOND_ADVICE_MESSAGE.getMessage()).chatId(chatId).build();
        }
        if (state.equals(BotCommand.FOUND_BOND)) {
            reply = sender.getBondInfo(message, user);
            if (reply.getText().startsWith("Error")) {
                return reply;
            }

            user.setCommand(BotCommand.DEFAULT);
        }
        service.saveCondition(user);

        return reply;
    }

    @Override
    public BotCommand getHandlerName() {
        return BotCommand.FIND_BOND;
    }
}
