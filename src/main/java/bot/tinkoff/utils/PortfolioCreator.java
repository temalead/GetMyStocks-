package bot.tinkoff.utils;

import bot.entity.Portfolio;
import bot.entity.User;
import bot.entity.dto.SecurityDto;
import bot.repository.UserService;
import bot.tinkoff.BondService;
import bot.tinkoff.ShareService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PortfolioCreator {
    ShareService shareService;
    BondService bondService;
    UserService service;


    public List<SecurityDto> createPortfolio(Message message) {
        String chatId = message.getChatId().toString();
        String text = message.getText();
        User user = service.getUserOrCreateNewUserByChatId(chatId);
        Portfolio portfolio=new Portfolio();
        List<SecurityDto> result = new ArrayList<>();

        String[] securities = text.split(", ");
        for (String security : securities) {
            if (security.contains("ОФЗ")) {
                String[] securityResult = security.split("-");
                String securityName = securityResult[0];
                String lot = securityResult[1];
                result.add(SecurityDtoTranslator.translateToSecurityDto(bondService.getInfo(securityName),lot,false));
            } else {
                String[] securityResult = security.split("-");
                String securityName = securityResult[0];
                String lot = securityResult[1];
                result.add(SecurityDtoTranslator.translateToSecurityDto(shareService.getInfo(securityName),lot,true));
            }
        }


        return result;
    }
}
