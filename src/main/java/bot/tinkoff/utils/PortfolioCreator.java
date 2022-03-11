package bot.tinkoff.utils;

import bot.entity.Portfolio;
import bot.entity.User;
import bot.entity.dto.SecurityDto;
import bot.exception.sender.Asset;
import bot.repository.UserService;
import bot.tinkoff.BondService;
import bot.tinkoff.ShareService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class PortfolioCreator {
    ShareService shareService;
    BondService bondService;
    UserService service;
    FractionOccupiedPortfolioCalculator portfolioCalculator;


    public Portfolio createPortfolio(Message message) {
        log.info("Creating new user portfolio");
        String chatId = message.getChatId().toString();
        String text = message.getText();
        User user = service.getUserOrCreateNewUserByChatId(chatId);
        Portfolio portfolio=new Portfolio();
        List<SecurityDto> result = new ArrayList<>();

        String[] securities = text.split(", ");
        for (String security : securities) {
            String[] securityResult = security.split("-");
            String securityName = securityResult[0];
            if (security.contains("ОФЗ")) {
                log.info("Security {}", (Object[]) securityResult);
                BigDecimal lot = BigDecimal.valueOf(Long.parseLong(securityResult[1]));
                result.add(SecurityDtoTranslator.translateToSecurityDto(bondService.getInfo(securityName),lot, Asset.BOND));
            } else {
                BigDecimal lot = BigDecimal.valueOf(Long.parseLong(securityResult[1]));
                result.add(SecurityDtoTranslator.translateToSecurityDto(shareService.getInfo(securityName),lot,Asset.SHARE));
            }
        }
        portfolio.setSecurityList(result);
        log.info("Portfolio {}",portfolio.getSecurityList());
        BigDecimal value = portfolioCalculator.calculatePortfolioValue(portfolio,user);
        portfolio.setPortfolioValue(value);

        user.setPortfolio(portfolio);
        service.saveCondition(user);

        return portfolio;
    }
}
