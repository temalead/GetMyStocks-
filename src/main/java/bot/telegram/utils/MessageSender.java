package bot.telegram.utils;

import bot.domain.BondDto;
import bot.domain.Stock;
import bot.exception.sender.Assets;
import bot.exception.sender.NotFoundMessageBuilder;
import bot.tinkoff.BondService;
import bot.tinkoff.ShareService;
import bot.tinkoff.utils.ShareInfoSender;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.concurrent.CompletionException;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MessageSender {
    ShareService shareService;
    BondService bondService;


    public SendMessage getShareInfo(Message message) {
        String ticker = message.getText();
        String chatId = message.getChatId().toString();
        try {
            Stock share = shareService.getInfo(ticker);
            String result = ShareInfoSender.createMessage(share);
            return SendMessage.builder().chatId(chatId).text(result).build();
        } catch (CompletionException e) {
            return SendMessage.builder().chatId(chatId).text(NotFoundMessageBuilder.createMessageError(ticker, Assets.SHARE)).build();
        }
    }

    public SendMessage getBondInfo(Message message){
        String chatId = message.getChatId().toString();
        BondDto bondDto = bondService.getInfo(message.getText());
        return SendMessage.builder().chatId(chatId).text(bondDto.toString()).build();
    }
}
