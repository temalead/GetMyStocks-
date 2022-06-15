package stock_service.utils;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import stock_service.entity.Portfolio;
import stock_service.entity.User;
import stock_service.entity.dto.SecurityDto;
import stock_service.entity.Asset;
import stock_service.repository.UserRepository;
import stock_service.service.BondService;
import stock_service.service.ShareService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class PortfolioService {
    ShareService shareService;
    BondService bondService;
    UserRepository repository;
    FractionOccupiedPortfolioCalculator portfolioCalculator;


    public Portfolio getInfo(String text, User user) {
        log.info("Creating new user portfolio");
        Portfolio portfolio = new Portfolio();
        List<SecurityDto> result = new ArrayList<>();

        String[] securities = text.split(", ");
        for (String security : securities) {
            String[] securityResult = security.split("-");
            String securityName = securityResult[0];
            if (security.contains("ОФЗ")) {
                log.info("Security {}", (Object[]) securityResult);
                BigDecimal lot = BigDecimal.valueOf(Long.parseLong(securityResult[1]));
                result.add(SecurityDtoTranslator.translateToSecurityDto(bondService.getAssetFromTinkoffByTicker(securityName), lot, Asset.BOND));
            } else {
                BigDecimal lot = BigDecimal.valueOf(Long.parseLong(securityResult[1]));
                result.add(SecurityDtoTranslator.translateToSecurityDto(shareService.getAssetFromTinkoffByTicker(securityName), lot, Asset.SHARE));
            }
        }
        portfolio.setSecurities(result);
        log.info("Portfolio {}", portfolio.getSecurities());
        BigDecimal value = portfolioCalculator.calculatePortfolioValue(portfolio);
        portfolio.setPortfolioValue(value);
        user.setPortfolio(portfolio);

        repository.save(user);

        log.info("User {}", user);

        return portfolio;
    }
}
