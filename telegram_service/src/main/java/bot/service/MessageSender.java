package bot.service;

import bot.entity.User;
import bot.exception.PortfolioCompositionValidationException;
import bot.exception.PortfolioNotFoundException;
import bot.exception.sender.Asset;
import bot.exception.sender.NotFoundMessageBuilder;
import bot.service.sender.BondSender;
import bot.service.sender.PortfolioSender;
import bot.service.sender.ShareSender;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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


    private final ShareSender shareSender;
    private final BondSender bondSender;

    private final PortfolioSender portfolioSender;

    public SendMessage getShareInfo(Message message) {
        String ticker = message.getText();
        String chatId = message.getChatId().toString();
        try {

            return shareSender.getInfo(chatId);

        } catch (CompletionException e) {
            return SendMessage.builder().chatId(chatId).text(NotFoundMessageBuilder.createMessageError(ticker, Asset.SHARE)).build();
        }
    }

    public SendMessage getBondInfo(Message message) {
        String ticker = message.getText();
        String chatId = message.getChatId().toString();
        try {

            return bondSender.getInfo(chatId);

        } catch (CompletionException e) {
            return SendMessage.builder().chatId(chatId).text(NotFoundMessageBuilder.createMessageError(ticker, Asset.BOND)).build();
        }
    }


    public SendMessage getPortfolioInfo(String chatId) {
        try {
            return portfolioSender.getInfo(chatId);
        }
        catch (Exception ex){
            return SendMessage.builder().chatId(chatId).text(NotFoundMessageBuilder.createPortfolioError()).build();
        }
    }
}
