package bot.tinkoff.sender;

import bot.domain.BondDto;
import bot.tinkoff.utils.BondMessageCreator;
import bot.tinkoff.BondService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
@Slf4j
public class BondSender implements Sender{
    private final BondService bondService;

    @Override
    public SendMessage getInfo(Message message) {
        String chatId = message.getChatId().toString();
        String text = message.getText();
        BondDto info = bondService.getInfo(text);
        String result= BondMessageCreator.createMessage(info);
        log.info("Message: {}",result);
        return SendMessage.builder().chatId(chatId).text(result).build();
    }
}
