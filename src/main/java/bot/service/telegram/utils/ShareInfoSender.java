package bot.service.telegram.utils;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.math.BigDecimal;

public abstract class ShareInfoSender {
    public static String createMessage(String text, BigDecimal dividend) {
        return String.format("%s: %s ",text,dividend);
    }

}
