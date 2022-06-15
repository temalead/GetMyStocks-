package bot.service.sender;


import bot.exception.NonExistentPortfolioException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface SecuritySender {
    SendMessage getInfo(String chatId) throws NonExistentPortfolioException;


}
