package stock_service.utils;



import stock_service.entity.Security;
import stock_service.entity.dto.SecurityDto;
import stock_service.exception.sender.Asset;

import java.math.BigDecimal;

public class SecurityDtoTranslator {

    public static SecurityDto translateToSecurityDto(Security security, BigDecimal lot, Asset asset){
       return new SecurityDto(security.getPrice(),
               security.getId(),
               lot,
               asset);
    }
}
