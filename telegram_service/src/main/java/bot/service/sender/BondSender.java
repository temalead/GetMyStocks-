package bot.service.sender;


import bot.entity.MyBond;
import bot.exception.BondNotFoundException;
import bot.kafka.BondResponseConsumer;
import bot.repository.BondRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
@Slf4j
public class BondSender implements SecuritySender {

    private final BondResponseConsumer consumer;

    @Override
    @SneakyThrows
    public SendMessage getInfo(Message message) {
        String chatId = message.getChatId().toString();


        ExecutorService service = Executors.newSingleThreadExecutor();
        String result = service.submit(consumer).get();

        return SendMessage.builder().chatId(chatId).text(result).build();

    }

}
