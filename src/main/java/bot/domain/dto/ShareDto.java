package bot.domain.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Accessors(chain = true)
@Data
public class ShareDto {
    private String figi;
    private String ticker;
}
