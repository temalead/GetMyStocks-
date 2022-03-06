package bot.domain;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Portfolio {
    List<ShareDto> shares;
    List<BondDto> bondDtos;
}
