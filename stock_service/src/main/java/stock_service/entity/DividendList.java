package stock_service.entity;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class DividendList {
    List<Dividend> dividends;
    
    
    public DividendList getDefaultDividends(){
        return new DividendList(List.of(Dividend.getDefaultDividend()));
    }
}
