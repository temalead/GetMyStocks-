package bot.tinkoff;

import bot.domain.BondDto;
import bot.exception.BondNotFoundException;
import bot.repository.BondRepository;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.Bond;
import ru.tinkoff.piapi.core.InvestApi;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class BondService {
    InvestApi api;
    BondRepository repository;
    String classCode = "TQBR";

    @NonNull
    public BondDto getInfo(String name) {
        Optional<BondDto> bond = repository.findByName(name);
        if (bond.isPresent()){
            return bond.get();
        }
        else{
            Bond response = findBondByNameFromTinkoff(name);
            return createBond(response);
        }
    }


    private Bond findBondByNameFromTinkoff(String requestName) {
        Optional<Bond> response = api.getInstrumentsService().getAllBondsSync().stream().filter(bond -> bond.getName().equals(requestName)).findFirst();
        return response.orElseThrow(() -> new BondNotFoundException(requestName));
    }

    private BondDto createBond(Bond bond) {
        BondDto result = new BondDto();
        result.setName(bond.getName())
                .setPrice(BigDecimal.valueOf(bond.getPlacementPrice().getUnits()))
                .setLot(bond.getLot());

        repository.save(result);

        return result;
    }
}
