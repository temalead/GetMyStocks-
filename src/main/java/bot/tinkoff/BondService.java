package bot.tinkoff;

import bot.domain.MyBond;
import bot.exception.BondNotFoundException;
import bot.repository.BondRepository;
import bot.tinkoff.utils.PriceCalculator;
import com.google.protobuf.Timestamp;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.Bond;
import ru.tinkoff.piapi.core.InvestApi;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class BondService {
    InvestApi api;
    BondRepository repository;

    @NonNull
    public MyBond getInfo(String name) {
        Optional<MyBond> foundBond = repository.findById(name);
        if (foundBond.isPresent()) {
            MyBond bond = foundBond.get();
            log.info("Found bond in cache {}", bond.getId());
            updateBond(bond);
            return bond;
        } else {
            Bond response = findBondByNameFromTinkoff(name);
            log.info("Adding new bond in cache {}", response.getName());
            return createBond(response);
        }
    }

    public List<MyBond> createBondCollection(List<String> tickers) {
        List<MyBond> result = new ArrayList<>();
        tickers.forEach(ticker -> result.add(getInfo(ticker)));
        return result;
    }


    private Bond findBondByNameFromTinkoff(String requestName) {
        Optional<Bond> response = api.getInstrumentsService().getAllBondsSync().stream().filter(bond -> bond.getName().equals(requestName)).findFirst();
        log.info("Getting bond {} from tinkoff api", requestName);
        return response.orElseThrow(() -> new BondNotFoundException(requestName));
    }

    private MyBond createBond(Bond bond) {
        MyBond result = new MyBond();
        result.setId(bond.getName());
        result.setPrice(getPrice(bond).multiply(BigDecimal.valueOf(10)));
        result.setLot(bond.getLot())
                .setMaturityDate(getMaturityDate(bond))
                .setAci(PriceCalculator.calculateACI(bond.getAciValue()))
                .setFigi(bond.getFigi());

        repository.save(result);

        return result;
    }

    private void updateBond(MyBond myBond) {
        Bond bond = api.getInstrumentsService().getBondByFigi(myBond.getFigi()).join().get();
        myBond.setPrice(getPrice(bond));
        repository.save(myBond);
    }


    private LocalDate getMaturityDate(Bond bond) {
        Timestamp date = bond.getMaturityDate();
        return Instant.ofEpochSecond(date.getSeconds()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private BigDecimal getPrice(Bond bond) {
        return PriceCalculator.calculateValue(api.getMarketDataService().getLastPrices(Collections.singleton(bond.getFigi())).join().get(0).getPrice());
    }
}
