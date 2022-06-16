package bot.service.sender;


import bot.kafka.ShareResponseConsumer;
import bot.utils.KafkaResultReceiver;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class ShareSender implements SecuritySender {

    private final ShareResponseConsumer consumer;


    @Override
    @SneakyThrows
    public SendMessage getInfo(String chatId) {

        String result = KafkaResultReceiver.getResult(consumer);


        return SendMessage.builder().chatId(chatId).text(result).build();


    }
}



