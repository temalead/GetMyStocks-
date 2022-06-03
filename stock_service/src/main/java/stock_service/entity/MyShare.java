package stock_service.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;
import stock_service.entity.dto.DividendListDto;

import java.io.Serializable;
import java.util.Objects;

@RequiredArgsConstructor
@Accessors(chain = true)
@RedisHash("Share")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyShare extends Security implements Serializable {
    String name;
    String figi;
    DividendListDto dividends;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyShare myShare = (MyShare) o;
        return Objects.equals(id, myShare.id);
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
