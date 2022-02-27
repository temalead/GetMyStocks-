package bot.tinkoff.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import ru.tinkoff.piapi.core.InvestApi;

@RequiredArgsConstructor
public abstract class AbstractService {
    private final InvestApi api;
    @Value("${bot.invest.code}")
    private String classCode;

    public InvestApi api(){
        return api;
    }
}
