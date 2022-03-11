package bot.telegram.utils;

import bot.entity.Portfolio;
import bot.entity.User;
import bot.exception.BondNotFoundException;
import bot.exception.NonExistentPortfolioException;
import bot.exception.sender.Asset;
import bot.exception.sender.NonExistentPortfolioMessage;
import bot.exception.sender.NotFoundMessageBuilder;
import bot.tinkoff.sender.BondSender;
import bot.tinkoff.sender.PortfolioCompositionSender;
import bot.tinkoff.sender.ShareSender;
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
    ShareSender shareSender;
    BondSender bondSender;
    PortfolioCompositionSender compositionSender;


    public SendMessage getShareInfo(Message message, User user) {
        String ticker = message.getText();
        String chatId = message.getChatId().toString();
        try {
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
    }

}
