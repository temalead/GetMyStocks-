package bot.domain.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Accessors(chain = true)
@RedisHash("Share")
@Getter
@Setter
public class ShareDto implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String figi;
    private String ticker;
    private BigDecimal dividend;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShareDto shareDto = (ShareDto) o;
        return Objects.equals(ticker, shareDto.ticker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticker);
    }
}
