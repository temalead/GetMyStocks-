package bot.service.sender;


import bot.kafka.ShareResponseConsumer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class ShareSender implements SecuritySender {

    private final ShareResponseConsumer consumer;


    @Override
    @SneakyThrows
    public SendMessage getInfo(Message message) {
        String chatId = message.getChatId().toString();

        ExecutorService service = Executors.newSingleThreadExecutor();
        String result = service.submit(consumer).get();
        consumer.shutdown();


        return SendMessage.builder().chatId(chatId).text(result).build();


    }
}



