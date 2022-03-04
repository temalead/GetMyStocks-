package bot.tinkoff.sender;

import bot.domain.Stock;
import bot.tinkoff.ShareService;
import bot.tinkoff.utils.ShareInfoSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class ShareSender implements Sender{
    private final ShareService service;

    @Override
    public BotApiMethod<Message> getInfo(Message message) {
        String chatId = message.getChatId().toString();

        Stock info = service.getInfo(message.getText());
        String result = ShareInfoSender.createMessage(info);
        return SendMessage.builder().chatId(chatId).text(result).build();

    }
}
