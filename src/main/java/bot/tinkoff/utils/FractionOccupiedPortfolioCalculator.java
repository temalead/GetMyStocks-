package bot.tinkoff.utils;

import bot.entity.Portfolio;
import bot.entity.User;
import bot.entity.dto.SecurityDto;
import bot.exception.sender.Asset;
import bot.repository.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class FractionOccupiedPortfolioCalculator {
    UserService service;

    public String calculateOccupiedFractionOfSecurityByUser(SecurityDto security,User user) {
        Portfolio portfolio = user.getPortfolio();
        BigDecimal portfolioValue = portfolio.getPortfolioValue();
        BigDecimal securityValue = security.getPrice().multiply(security.getLot());


        float v = securityValue.floatValue() / portfolioValue.floatValue();
        BigDecimal fraction = BigDecimal.valueOf(v).multiply(BigDecimal.valueOf(100));

        security.setFraction(fraction);



        return "Fraction of Portfolio: " +
                String.format("%.2f ", fraction.floatValue())+"%";
    }


    public BigDecimal calculatePortfolioValue(Portfolio portfolio, User user) {

        final BigDecimal[] portfolioValue = {BigDecimal.ZERO};

        List<SecurityDto> list = portfolio.getSecurityList();

        list.forEach(security -> portfolioValue[0] = portfolioValue[0].add(calculateSecurityValue(security)));

        portfolio.setPortfolioValue(portfolioValue[0]);

        user.setPortfolio(portfolio);
        service.saveCondition(user);


        return portfolioValue[0];
    }

    private BigDecimal calculateSecurityValue(SecurityDto security) {
        return security.getPrice().multiply(security.getLot());
    }



    public String calculateFraction(Portfolio portfolio, Asset asset){
        BigDecimal dividend = findAssetByRequest(portfolio, asset);
        BigDecimal divisor = portfolio.getPortfolioValue();

        float result = dividend.floatValue() / divisor.floatValue()*100;
        return String.format("%s: %.2f",asset,result)+"%";
    }

    private BigDecimal findAssetByRequest(Portfolio portfolioDto, Asset asset) {

        List<SecurityDto> portfolio = portfolioDto.getSecurityList();
        final BigDecimal[] result = {BigDecimal.ZERO};


        portfolio.stream().filter(securityDto -> securityDto.getAsset().equals(asset))
                .forEach(securityDto -> result[0] = result[0].add(calculateSecurityValue(securityDto)));


        return result[0];
    }
}
