package bot.telegram.utils;

import bot.domain.dto.SecurityDto;
import bot.tinkoff.BondService;
import bot.tinkoff.ShareService;
import bot.tinkoff.utils.SecurityDtoTranslator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor

public class PortfolioCreator {
    private final ShareService shareService;
    private final BondService bondService;

    public List<SecurityDto> createPortfolio(String text){
        List<SecurityDto> result=new ArrayList<>();

        String[] securities = text.split(", ");
        for (String security : securities) {
            if (security.contains("ОФЗ")){
                result.add(SecurityDtoTranslator.translateToSecurityDto(bondService.getInfo(security)));
            }
            else{
               result.add( SecurityDtoTranslator.translateToSecurityDto(shareService.getInfo(security)));
            }
        }

        return result;
    }
}
