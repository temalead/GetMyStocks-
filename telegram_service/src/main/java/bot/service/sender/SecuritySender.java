package bot.service.sender;


import bot.entity.User;
import bot.exception.NonExistentPortfolioException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface SecuritySender {
    SendMessage getInfo(Message message) throws NonExistentPortfolioException;


}
