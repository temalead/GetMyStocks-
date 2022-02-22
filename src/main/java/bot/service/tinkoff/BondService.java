package bot.service.tinkoff;

import bot.domain.BondDto;
import bot.exception.NotFoundBondException;
import bot.repository.BondRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.core.InvestApi;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BondService{
    private final InvestApi api;
    private final BondRepository repository;
    @Value("${bot.invest.code}")
    private String classCode;


    public BigDecimal getNKD(String ticker){
        Optional<BondDto> bondOpt = repository.findById(ticker);
        if (bondOpt.isPresent()){
            return bondOpt.get().getNkd();
        }
        else{
            BondDto bond = createBond(ticker);
            repository.save(bond);
            return bond.getNkd();
        }
    }

    private BondDto createBond(String ticker) {
        return null;
    }
}
