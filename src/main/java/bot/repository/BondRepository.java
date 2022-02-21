package bot.repository;

import bot.domain.BondDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BondRepository extends CrudRepository<BondDto, String> {
}
