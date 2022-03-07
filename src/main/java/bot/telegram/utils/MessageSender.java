package bot.telegram.utils;

import bot.domain.dto.SecurityDto;
import bot.exception.BondNotFoundException;
import bot.exception.ValidateDataException;
import bot.exception.sender.Assets;
import bot.exception.sender.NotFoundMessageBuilder;
import bot.tinkoff.sender.BondSender;
import bot.tinkoff.sender.ShareSender;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.concurrent.CompletionException;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MessageSender {
    ShareSender shareSender;
    BondSender bondSender;
    PortfolioCreator creator;


    public SendMessage getShareInfo(Message message) {
        String ticker = message.getText();
        String chatId = message.getChatId().toString();
        try {
            return shareSender.getInfo(message);
        } catch (CompletionException e) {
            return SendMessage.builder().chatId(chatId).text(NotFoundMessageBuilder.createMessageError(ticker, Assets.SHARE)).build();
        }
    }

    public SendMessage getBondInfo(Message message) {
        String chatId = message.getChatId().toString();
        String text = message.getText();
        try {
            return bondSender.getInfo(message);
        } catch (BondNotFoundException e) {
            return SendMessage.builder().chatId(chatId).text(NotFoundMessageBuilder.createMessageError(text, Assets.BOND)).build();
        }
    }

    public SendMessage getCreatedPortfolio(Message message){
        String chatId = message.getChatId().toString();
        String text = message.getText();
        try {
            List<SecurityDto> portfolio = creator.createPortfolio(text);
            return SendMessage.builder().chatId(chatId)
                    .text(portfolio.toString())
                    .build();
        }catch (ValidateDataException e){
            return SendMessage.builder().text(e.getCause().toString()).chatId(chatId).build();
        }
    }
}
