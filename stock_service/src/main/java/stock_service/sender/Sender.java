package stock_service.sender;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import stock_service.entity.User;
import stock_service.exception.NonExistentPortfolioException;

public interface Sender {
    void getInfo(Message message, User user) throws NonExistentPortfolioException;


}
