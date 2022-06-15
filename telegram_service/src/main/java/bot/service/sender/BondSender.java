package bot.service.sender;


import bot.kafka.BondResponseConsumer;
import bot.utils.KafkaResultReceiver;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@RequiredArgsConstructor
@Slf4j
public class BondSender implements SecuritySender {

    private final BondResponseConsumer consumer;

    @Override
    @SneakyThrows
    public SendMessage getInfo(String chatId) {

        String result = KafkaResultReceiver.getResult(consumer);


        return SendMessage.builder().chatId(chatId).text(result).build();

    }

}
