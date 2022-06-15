package bot.service.sender;


import bot.kafka.PortfolioResponseConsumer;
import bot.kafka.ShareResponseConsumer;
import bot.utils.KafkaResultReceiver;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PortfolioSender {
    private final PortfolioResponseConsumer consumer;


    public SendMessage getInfo(String chatId) {


        String result= KafkaResultReceiver.getResult(consumer);

        return SendMessage.builder().chatId(chatId).text(result).build();

    }
}
