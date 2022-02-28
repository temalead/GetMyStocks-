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
            case WANNA_GET_SHARE:
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
        SendMessage reply= new SendMessage();
        reply.setChatId(chatId);

        switch (state){
            case UNRECOGNIZED:
                reply.setText(BotMessageSendHinter.UNRECOGNIZED.getMessage());
                break;
            case SHOW_HELP_MENU:
                reply.setText(BotMessageSendHinter.HELP_MESSAGE.getMessage());
                break;
            case SHOW_START_MENU:
                reply.setText(BotMessageSendHinter.START_MESSAGE.getMessage());
                break;
        }
        reply.enableMarkdown(true);
        reply.setReplyMarkup(menu.getMainMenuKeyboard());
        return reply;
    }


    private SendMessage processSearchBond(BotState state, Message message) {
        return null;
    }


    private SendMessage processSearchShare(BotState state, Message message) {
        SendMessage result = null;

        String chatId = message.getChatId().toString();
        if (state.equals(BotState.WANNA_GET_SHARE)) {
            result = SendMessage.builder().text(BotMessageSendHinter.SEND_SHARE.getMessage()).chatId(chatId).build();
        }
        if (state.equals(BotState.FIND_SHARE)){
            result=sender.getShareInfo(message);
        }
        return result;
    }
}
