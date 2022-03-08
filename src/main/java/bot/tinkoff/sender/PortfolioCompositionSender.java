package bot.tinkoff.sender;

import bot.domain.dto.SecurityDto;
import bot.tinkoff.BondService;
import bot.tinkoff.utils.PortfolioCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class PortfolioCompositionSender implements Sender{
    private final PortfolioCreator creator;


    @Override
    public SendMessage getInfo(Message message) {

        List<SecurityDto> portfolio = creator.createPortfolio(message.getText());

        for (SecurityDto security : portfolio) {

        }

        return null;
    }
}
