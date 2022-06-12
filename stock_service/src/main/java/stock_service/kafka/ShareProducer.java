package stock_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import stock_service.config.TopicsProperties;
import stock_service.entity.MyShare;
import stock_service.repository.ShareRepository;
import stock_service.service.ShareService;

@Service
@RequiredArgsConstructor
public class ShareProducer {

    private final ShareService service;

    private final ShareRepository repository;


    @SneakyThrows
    public void sendResponse(String requestedShare) {

        MyShare resultShare = service.getInfo(requestedShare);

        repository.save(resultShare);
    }
}
