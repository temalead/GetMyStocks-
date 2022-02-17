package bot.repository;

import bot.domain.dto.ShareDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ShareRepoImpl {
    private final ShareRepository repository;


    /**
     * @param ticker
     * @return Share by ticker
     * BUT possibly Bad performance. How can i get value by not key in redis cache?
     */
    public Optional<ShareDto> getShareByTicker(String ticker) {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .filter(shareDto -> shareDto.getTicker().equals(ticker)).findAny();

    }

    public ShareDto save(ShareDto shareDto) {
        return repository.save(shareDto);
    }
}
