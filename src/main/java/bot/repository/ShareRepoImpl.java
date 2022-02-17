package bot.repository;

import bot.domain.dto.ShareDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShareRepoImpl {
    private final ShareRepository repository;

    public Optional<ShareDto> getShareByTicker(String ticker){
        Iterable<ShareDto> all = repository.findAll();
        for (ShareDto shareDto : all) {
            if (shareDto.getTicker().equals(ticker)) return Optional.of(shareDto);
        }
        return Optional.empty();
    }
    public ShareDto save(ShareDto shareDto){
        return repository.save(shareDto);
    }
}
