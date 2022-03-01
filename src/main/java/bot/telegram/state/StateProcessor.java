package bot.telegram.state;

import bot.domain.User;
import bot.repository.UserService;
import bot.telegram.keyboard.MainMenuKeyboard;
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
        switch (state) {
            case WANNA_GET_SHARE:
            case FIND_SHARE:
                return processSearchShare(message);
            case WANNA_GET_BOND:
                return processSearchBond(message);
            case MAKE_PORTFOLIO:
            case GET_PORTFOLIO:
            case DELETE_PORTFOLIO:
            case UPDATE_PORTFOLIO:
                return null;
            case UNRECOGNIZED:
            case GET_HELP:
            case GET_START_MENU:
                return processHintMessage(message.getChatId().toString());
        }

        return null;
    }

    private SendMessage processHintMessage(String chatId) {
        User user = service.initializeUser(chatId);
        BotState state = user.getState();
        SendMessage reply = SendMessage.builder().chatId(chatId).text(state.name()).build();

        switch (state) {
            case UNRECOGNIZED:
                reply.setText(BotMessageSendHinter.UNRECOGNIZED_MESSAGE.getMessage());
                break;
            case GET_START_MENU:
                reply.setText(BotMessageSendHinter.START_MESSAGE.getMessage());
                break;
            default:
                reply.setText(BotMessageSendHinter.HELP_MESSAGE.getMessage());
        }
        user.setState(BotState.NONE);
        reply.enableMarkdown(true);
        reply.setReplyMarkup(menu.getMainMenuKeyboard());

        log.info("Message sent: {}", reply.getText());


        service.saveCondition(user);

        return reply;

    }


    private SendMessage processSearchShare(Message message) {
        User user = service.initializeUser(message.getChatId().toString());
        BotState state = user.getState();

        SendMessage reply = null;

        String chatId = message.getChatId().toString();
        if (state.equals(BotState.WANNA_GET_SHARE)) {
            user.setState(BotState.FIND_SHARE);
            reply = SendMessage.builder().text(BotMessageSendHinter.SHARE_ADVICE_MESSAGE.getMessage()).chatId(chatId).build();
        }
        if (state.equals(BotState.FIND_SHARE)) {
            reply = sender.getShareInfo(message);
            if (reply.getText().startsWith("Share")){
                return reply;
            }
            user.setState(BotState.NONE);
        }
        service.saveCondition(user);

        return reply;
    }


    private SendMessage processSearchBond(Message message) {
        return null;
    }

}
