package bot.service.telegram.utils;

import bot.domain.ShareDto;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.math.BigDecimal;

public abstract class ShareInfoSender {
    public static String createMessage(String ticker, ShareDto share) {
        BigDecimal dividend = share.getDividend();
        BigDecimal price = share.getPrice();

    }

}
