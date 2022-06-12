package stock_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import stock_service.entity.MyBond;
import stock_service.repository.BondRepository;
import stock_service.service.BondService;

@Component
@RequiredArgsConstructor
public class BondProducer {

    private final BondService service;
    private final BondRepository repository;

    @SneakyThrows
    public void sendResponse(String requestedShare) {

        MyBond result = service.getInfo(requestedShare);

        repository.save(result);
    }
}
