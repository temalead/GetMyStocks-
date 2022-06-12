package bot.service.sender;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class ShareSender implements SecuritySender {
    private final String SHARE_RES_TOPIC = "share_res.topic";

    private ShareRedundant redundant;

    @Override
    @SneakyThrows
    public SendMessage getInfo(Message message) {
        String chatId = message.getChatId().toString();

        String result= getMessage();
        return SendMessage.builder().chatId(chatId).text(result).build();


    }

    private String getMessage() {
        return redundant.getKafkaMessage();
    }
}



