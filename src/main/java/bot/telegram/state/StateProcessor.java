package bot.telegram.state;

import bot.domain.User;
import bot.repository.UserService;
import bot.telegram.keyboard.MainMenuKeyboard;
import bot.telegram.model.BotMessageSend;
import bot.telegram.utils.MessageSender;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StateProcessor {
    MessageSender sender;
    MainMenuKeyboard menu;
    UserService service;

    public SendMessage processMessage(BotState state, Message message) {
        String chatId = message.getChatId().toString();
        switch (state) {

            /*case WANNA_GET_SHARE:
            case FIND_SHARE:
                return processSearchShare(message);*/

            case WANNA_GET_BOND:
                return processSearchBond(message);

            case MAKE_PORTFOLIO:
            case GET_PORTFOLIO:
            case DELETE_PORTFOLIO:
            case UPDATE_PORTFOLIO:
                return null;

            case UNRECOGNIZED:
            case GET_START_MENU:
            case GET_HELP:
                return processHintMessage(chatId);
        }

        return null;
    }

    private SendMessage processHintMessage(String chatId) {
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


        log.info("Message sent: {}", reply.getText());


        service.saveCondition(user);

        return reply;

    }


    private SendMessage processSearchBond(Message message) {
        return null;
    }

}
