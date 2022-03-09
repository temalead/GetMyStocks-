package bot.entity.dto;

import bot.exception.sender.Asset;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@AllArgsConstructor
@Accessors(chain = true)
@Data
public class SecurityDto {
    BigDecimal price;
    String name;
    BigDecimal lot;
    Asset asset;
    BigDecimal fraction;


    public SecurityDto(BigDecimal price, String name, BigDecimal lot, Asset asset) {
        this.price = price;
        this.name = name;
        this.lot = lot;
        this.asset = asset;
    }
}
