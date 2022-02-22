package bot.domain;


import bot.domain.dto.DividendListDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.redis.core.RedisHash;
import ru.tinkoff.piapi.contract.v1.Dividend;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@RequiredArgsConstructor
@Accessors(chain = true)
@RedisHash("Share")
@Getter
@Setter
public class ShareDto implements Serializable {
    @Id
    private String id;
    private String figi;
    private DividendListDto dividends;
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShareDto shareDto = (ShareDto) o;
        return Objects.equals(id, shareDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ShareDto{" +
                "ticker='" + id + '\'' +
                ", figi='" + figi + '\'' +
                ", dividends=" + dividends +
                ", price=" + price +
                '}';
    }
}
