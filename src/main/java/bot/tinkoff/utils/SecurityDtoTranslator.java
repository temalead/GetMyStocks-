package bot.tinkoff.utils;

import bot.domain.Security;
import bot.domain.dto.SecurityDto;

public class SecurityDtoTranslator {

    public static SecurityDto translateToSecurityDto(Security security){
       return new SecurityDto(security.getPrice(),security.getId());
    }
}
