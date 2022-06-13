package bot.utils;


import bot.entity.Portfolio;
import bot.entity.Security;
import bot.entity.User;
import bot.entity.dto.SecurityDto;
import bot.exception.sender.Asset;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class FractionOccupiedPortfolioCalculator {

    /*public BigDecimal calculateOccupiedFractionOfSecurityByUser(Security security, User user) {
        Portfolio portfolio = user.getPortfolio();
        BigDecimal portfolioValue = portfolio.getPortfolioValue();
        BigDecimal securityValue = security.getPrice().multiply(BigDecimal.valueOf(security.getLot()));


        float v = securityValue.floatValue() / portfolioValue.floatValue();

        return BigDecimal.valueOf(v).multiply(BigDecimal.valueOf(100));
    }


    public BigDecimal calculatePortfolioValue(Portfolio portfolio) {
        log.info("Calculating portfolio value");

        final BigDecimal[] portfolioValue = {BigDecimal.ZERO};

        List<Security> list = portfolio.getSecurityList();

        list.forEach(security -> portfolioValue[0] = portfolioValue[0].add(calculateSecurityValue(security)));

        return portfolioValue[0];
    }

    private BigDecimal calculateSecurityValue(SecurityDto security) {
        return security.getPrice().multiply(security.getLot());
    }


    public String calculateFraction(Portfolio portfolio, Asset asset) {
        BigDecimal dividend = findAssetByRequest(portfolio, asset);
        BigDecimal divisor = portfolio.getPortfolioValue();

        float result = dividend.floatValue() / divisor.floatValue() * 100;
        return String.format("%s: %.2f", asset, result) + "%";
    }

    private BigDecimal findAssetByRequest(Portfolio portfolioDto, Asset asset) {

        List<Security> portfolio = portfolioDto.getSecurityList();
        final BigDecimal[] result = {BigDecimal.ZERO};


        portfolio.stream().filter(securityDto -> securityDto.getAsset().equals(asset))
                .forEach(securityDto -> result[0] = result[0].add(calculateSecurityValue(securityDto)));


        return result[0];
    }*/
}
