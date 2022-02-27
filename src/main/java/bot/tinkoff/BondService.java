package bot.tinkoff;

import bot.domain.BondDto;
import bot.exception.NotFoundBondException;
import bot.repository.BondRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.core.InvestApi;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BondService {
    InvestApi api;
    BondRepository repository;
    String classCode="TQBR";


    public BigDecimal getNKD(String ticker) {
        Optional<BondDto> bondOpt = repository.findById(ticker);
        if (bondOpt.isPresent()) {
            return bondOpt.get().getNkd();
        } else {
            BondDto bond = createBond(ticker);
            repository.save(bond);
            return bond.getNkd();
        }
    }

    private BondDto createBond(String ticker) {
        return null;
    }
}
