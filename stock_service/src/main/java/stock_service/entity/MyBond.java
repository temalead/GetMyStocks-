package stock_service.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@RequiredArgsConstructor
@Accessors(chain = true)
@RedisHash("Bond")
@Getter
@Setter
public class MyBond extends Security implements Serializable {
    private String figi;
    private BigDecimal aci;
    private LocalDate maturityDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyBond myBond = (MyBond) o;
        return Objects.equals(id, myBond.id);
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
