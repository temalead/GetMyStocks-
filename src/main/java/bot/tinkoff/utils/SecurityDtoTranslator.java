package bot.tinkoff.utils;

import bot.entity.Security;
import bot.entity.dto.SecurityDto;

public class SecurityDtoTranslator {

    public static SecurityDto translateToSecurityDto(Security security, String lot,boolean isShare){
       return new SecurityDto(security.getPrice(),security.getId(),Integer.valueOf(lot),isShare);
    }
}
