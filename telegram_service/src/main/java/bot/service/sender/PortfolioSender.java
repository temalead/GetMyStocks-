package bot.service.sender;


import bot.kafka.ShareResponseConsumer;
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
public class PortfolioSender {
    private final ShareResponseConsumer consumer;


    public SendMessage getInfo(Message message) {
        String chatId = String.valueOf(message.getChatId());

        return null;

    }
}
