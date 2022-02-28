package bot.telegram.state;

import bot.telegram.keyboard.MainMenuKeyboard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class StateController {
    private final MessageSender sender;
    private final MainMenuKeyboard menu;

    public SendMessage processMessage(BotState state, Message message) {
        switch (state) {
            case SEARCH_SHARE:
                return processSearchShare(state, message);
            case SEARCH_BOND:
                return processSearchBond(state, message);
            case MAKE_PORTFOLIO:
            case GET_PORTFOLIO:
            case DELETE_PORTFOLIO:
            case UPDATE_PORTFOLIO:
                return null;
            case UNRECOGNIZED:
            case SHOW_HELP_MENU:
            case SHOW_START_MENU:
                return processHintMessage(state,message.getChatId().toString());
        }

        return null;
    }

    private SendMessage processHintMessage(BotState state,String chatId) {
        SendMessage reply= SendMessage.builder().chatId(chatId).build();

        switch (state){
            case UNRECOGNIZED:
                reply.setText(BotMessageSendHinter.UNRECOGNIZED.getMessage());
            case SHOW_HELP_MENU:
                reply.setText(BotMessageSendHinter.HELP_MESSAGE.getMessage());
            case SHOW_START_MENU:
                reply.setText(BotMessageSendHinter.START_MESSAGE.getMessage());

        }
        reply.enableMarkdown(true);
        reply.setReplyMarkup(menu.getMainMenuKeyboard());
        return reply;
    }


    private SendMessage processSearchBond(BotState state, Message message) {
        return null;
    }

    private SendMessage processSearchShare(BotState state, Message message) {
        SendMessage result;

        String chatId = message.getChatId().toString();
        if (state.equals(BotState.SEARCH_SHARE)) {
            result = SendMessage.builder().text(BotMessageSendHinter.SEND_SHARE.getMessage()).chatId(chatId).build();
        } else {
            result = sender.sendShare(message);
        }
        return result;
    }
}
