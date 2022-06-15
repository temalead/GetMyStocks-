package stock_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stock_service.entity.MyBond;
import stock_service.exception.BondNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BondMessageCreator {

    private final BondService service;

    public String createMessage(String request) {
        MyBond bond;
        try {

            bond = service.getAssetFromTinkoffByTicker(request);
        } catch (BondNotFoundException ex) {
            return ex.getMessage();
        }


        StringBuilder stringBuilder = new StringBuilder();

        LocalDate date = bond.getMaturityDate();
        BigDecimal price = bond.getPrice();
        stringBuilder.append("Price: ").append(price).append("\n");
        stringBuilder.append("Maturity Date: ").append(date).append("\n");
        stringBuilder.append("Accrued interests: ").append(bond.getAci()).append("\n");

        return stringBuilder.toString();
    }
}
