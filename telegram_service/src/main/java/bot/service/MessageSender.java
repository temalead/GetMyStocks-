package bot.service;

import bot.entity.User;
import bot.kafka.RequestProducer;
import bot.kafka.ShareConsumer;
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

    private final ShareConsumer shareConsumer;
    private final RequestProducer producer;


    public SendMessage getShareInfo(Message message, User user) {
        String ticker = message.getText();
        String chatId = message.getChatId().toString();
        try {
            shareConsumer.getShareInfoFromKafka(message);
            return shareSender.getInfo(message,user);
        } catch (CompletionException e) {
            return SendMessage.builder().chatId(chatId).text(NotFoundMessageBuilder.createMessageError(ticker, Asset.SHARE)).build();
        }
    }





    /*ShareSender shareSender;
    BondSender bondSender;
    PortfolioCompositionSender compositionSender;

    SecurityConsumer securityConsumer;
    RequestProducer requestProducer;*/

    /*public SendMessage getShareInfo(Message message, User user) {
        String ticker = message.getText();
        String chatId = message.getChatId().toString();
        try {
            securityConsumer.produceRequest(message)
            return shareSender.getInfo(message,user);
        } catch (CompletionException e) {
            return SendMessage.builder().chatId(chatId).text(NotFoundMessageBuilder.createMessageError(ticker, Asset.SHARE)).build();
        }
    }

    public SendMessage getBondInfo(Message message, User user) {
        String chatId = message.getChatId().toString();
        String text = message.getText();
        try {
            return bondSender.getInfo(message,user);
        } catch (BondNotFoundException e) {
            return SendMessage.builder().chatId(chatId).text(NotFoundMessageBuilder.createMessageError(text, Asset.BOND)).build();
        }
    }

    public SendMessage getPortfolioInfo(Message message, User user)  {
        return compositionSender.getInfo(message,user);
    }*/

}