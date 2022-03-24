package bot.tinkoff.utils;

import bot.entity.Security;
import bot.entity.dto.SecurityDto;
import bot.exception.sender.Asset;

import java.math.BigDecimal;

public class SecurityDtoTranslator {

    public static SecurityDto translateToSecurityDto(Security security, BigDecimal lot, Asset asset){
       return new SecurityDto(security.getPrice(),
               security.getId(),
               lot,
               asset);
    }
}
