package stock_service.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class ACIListDto {
    List<ACIDto> aciList;
}