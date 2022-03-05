package bot.tinkoff.utils;

import bot.domain.BondDto;
import bot.domain.dto.ACIDto;
import bot.domain.dto.ACIListDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BondMessageCreator {
    public static String createMessage(BondDto bondDto) {
        StringBuilder stringBuilder=new StringBuilder();

        LocalDate date = bondDto.getMaturityDate();
        BigDecimal price = bondDto.getPrice();
        stringBuilder.append("Price: ").append(price).append("\n");
        stringBuilder.append("Maturity Date: ").append(date).append("\n");
        stringBuilder.append("Accrued interests: ").append(bondDto.getAci()).append("\n");

        return stringBuilder.toString();
    }
}
