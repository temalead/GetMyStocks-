package bot.tinkoff.sender;

import bot.domain.ShareDto;
import bot.tinkoff.ShareService;
import bot.tinkoff.utils.ShareMessageCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class ShareSender implements Sender{
    private final ShareService service;

    @Override
    public SendMessage getInfo(Message message) {
        String chatId = message.getChatId().toString();

        ShareDto info = service.getInfo(message.getText());
        String result = ShareMessageCreator.createMessage(info);
        return SendMessage.builder().chatId(chatId).text(result).build();

    }
}
