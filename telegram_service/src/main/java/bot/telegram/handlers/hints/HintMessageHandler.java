package bot.telegram.handlers.hints;

import bot.entity.User;
import bot.repository.UserService;
import bot.telegram.handlers.MessageHandler;
import bot.telegram.keyboard.MainMenuKeyboard;
import bot.telegram.buttons.BotMessageSend;
import bot.telegram.state.BotCommand;
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
public class HintMessageHandler implements MessageHandler {
    UserService service;
    MainMenuKeyboard menu;


    @Override
    public SendMessage sendMessageDependsOnState(Message message) {
        String chatId = message.getChatId().toString();
        User user = service.getUserOrCreateNewUserByChatId(chatId);
        BotCommand state = user.getState();
        SendMessage reply = SendMessage.builder().chatId(chatId).text(state.name()).build();

        switch (state) {
            case START:
                reply.setText(BotMessageSend.START_MESSAGE.getMessage());
                reply.enableMarkdown(true);
                reply.setReplyMarkup(menu.getKeyboard());
                break;
            case UNRECOGNIZED:
                reply.setText(BotMessageSend.UNRECOGNIZED_MESSAGE.getMessage());
                break;
            case HELP:
                reply.setText(BotMessageSend.HELP_MESSAGE.getMessage());
                reply.setReplyMarkup(menu.getKeyboard());
                break;
            case BACK:
                reply.setText("Going back...");
                reply.setReplyMarkup(menu.getKeyboard());
                break;
        }
        user.setState(BotCommand.DEFAULT);


        service.saveCondition(user);

        return reply;
    }

    @Override
    public BotCommand getHandlerName() {
        return BotCommand.HINT;
    }
}
