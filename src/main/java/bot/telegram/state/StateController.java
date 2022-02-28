package bot.telegram.state;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class StateController {
    private final MessageSender sender;

    public SendMessage processMessage(BotState state, Message message) {
        switch (state) {
            case SEARCH_SHARE:
                return processSearchShare(state, message);
        }
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
