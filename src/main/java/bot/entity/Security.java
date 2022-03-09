package bot.entity;

import lombok.experimental.Accessors;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;


@MappedSuperclass
@Accessors(chain = true)
public abstract class Security {
    @Id
    String id;
    BigDecimal price;
    Integer lot;

    public String getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getLot() {
        return lot;
    }

    public void setLot(Integer lot) {
        this.lot = lot;
    }
}
