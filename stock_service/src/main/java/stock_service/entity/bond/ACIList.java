package stock_service.entity.bond;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class ACIList {
    List<ACI> aciList;
}
