package bot.tinkoff.sender;

import bot.tinkoff.BondService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@RequiredArgsConstructor
public class BondSender implements Sender{
    private final BondService service;

    @Override
    public BotApiMethod<Message> getInfo(Message message) {
        return null;
    }
}
