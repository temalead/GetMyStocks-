package bot.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@RequiredArgsConstructor
@Accessors(chain = true)
@RedisHash("Bond")
@Getter
@Setter
public class BondDto implements Serializable {
    @Id
    private String id;
    private String figi;
    private BigDecimal price;
    private BigDecimal aci;
    private Integer lot;
    private LocalDate maturityDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BondDto bondDto = (BondDto) o;
        return Objects.equals(id, bondDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BondDto{" +
                ", name='" + id + '\'' +
                ", figi='" + figi + '\'' +
                ", price=" + price +
                ", ACI=" + aci +
                ", lot=" + lot +
                ", maturityDate=" + maturityDate +
                '}';
    }
}
