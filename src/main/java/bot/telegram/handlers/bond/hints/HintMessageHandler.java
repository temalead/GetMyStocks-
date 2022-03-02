package bot.telegram.handlers.bond.hints;

import bot.domain.User;
import bot.repository.UserService;
import bot.telegram.handlers.MessageHandler;
import bot.telegram.keyboard.MainMenuKeyboard;
import bot.telegram.model.BotMessageSend;
import bot.telegram.state.BotState;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Component
public class HintMessageHandler implements MessageHandler {
    UserService service;
    MainMenuKeyboard menu;


    @Override
    public SendMessage sendMessageDependsOnState(Message message) {
        String chatId = message.getChatId().toString();
        User user = service.getUserOrCreateNewUserByChatId(chatId);
        BotState state = user.getState();
        SendMessage reply = SendMessage.builder().chatId(chatId).text(state.name()).build();

        switch (state) {
            case GET_START_MENU:
                reply.setText(BotMessageSend.START_MESSAGE.getMessage());
                reply.enableMarkdown(true);
                reply.setReplyMarkup(menu.getMainMenuKeyboard());
                break;
            case UNRECOGNIZED:
                reply.setText(BotMessageSend.UNRECOGNIZED_MESSAGE.getMessage());
                break;
            case GET_HELP:
                reply.setText(BotMessageSend.HELP_MESSAGE.getMessage());
                reply.setReplyMarkup(menu.getMainMenuKeyboard());
                break;
        }
        user.setState(BotState.NONE);


        service.saveCondition(user);

        return reply;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.WANNA_GET_BOND;
    }
}