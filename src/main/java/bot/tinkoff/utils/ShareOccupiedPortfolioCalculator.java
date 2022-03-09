package bot.tinkoff.utils;

import bot.entity.MyBond;
import bot.entity.MyShare;
import bot.entity.Portfolio;
import bot.entity.Security;
import bot.entity.User;
import bot.entity.dto.SecurityDto;
import bot.repository.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ShareOccupiedPortfolioCalculator {
    UserService service;

    public String calculateOccupiedShareOfSecurityByUser(SecurityDto security, String chatId) {
        User user = service.getUserOrCreateNewUserByChatId(chatId);
        Portfolio portfolio=new Portfolio();
        BigDecimal result = calculatePrice(security, portfolio);


        return "Fraction of Portfolio: " +
                String.format("%.2f", result.floatValue());
    }

    private BigDecimal calculatePrice(SecurityDto securityDto, Portfolio portfolio) {
        BigDecimal portfolioValue = calculatePortfolioValue(portfolio);
        BigDecimal securityValue = securityDto.getPrice().multiply(BigDecimal.valueOf(securityDto.getLot()));

        return securityValue.divide(portfolioValue).multiply(BigDecimal.valueOf(100));
    }

    private BigDecimal calculatePortfolioValue(Portfolio portfolio) {
        List<MyShare> shares = portfolio.getShares();
        List<MyBond> bonds = portfolio.getMyBonds();

        BigDecimal fractionOfShare = BigDecimal.ZERO;
        shares
                .forEach(share -> {
                    fractionOfShare.add(calculateSecurityValue(share));
                });
        BigDecimal fractionOfBond = BigDecimal.ZERO;
        bonds
                .forEach(bond -> {
                    fractionOfBond.add(calculateSecurityValue(bond));
                });

        return fractionOfBond.add(fractionOfShare);
    }

    private BigDecimal calculateSecurityValue(Security security) {
        return security.getPrice().multiply(BigDecimal.valueOf(security.getLot()));
    }
}
