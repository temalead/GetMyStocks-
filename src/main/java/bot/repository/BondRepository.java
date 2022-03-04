package bot.repository;

import bot.domain.BondDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BondRepository extends CrudRepository<BondDto, String> {
    Optional<BondDto> findByName(String name);
}
