package bot.tinkoff.utils;

import bot.domain.MyBond;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BondMessageCreator {
    public static String createMessage(MyBond myBond) {
        StringBuilder stringBuilder=new StringBuilder();

        LocalDate date = myBond.getMaturityDate();
        BigDecimal price = myBond.getPrice();
        stringBuilder.append("Price: ").append(price).append("\n");
        stringBuilder.append("Maturity Date: ").append(date).append("\n");
        stringBuilder.append("Accrued interests: ").append(myBond.getAci()).append("\n");

        return stringBuilder.toString();
    }
}
