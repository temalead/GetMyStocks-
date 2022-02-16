package bot.repository;

import bot.domain.dto.ShareDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShareRepository extends CrudRepository<ShareDto, Long> {
    Optional<ShareDto> findByTicker(String ticker);
}
