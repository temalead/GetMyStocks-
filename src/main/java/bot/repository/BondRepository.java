package bot.repository;

import bot.domain.MyBond;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BondRepository extends CrudRepository<MyBond, String> {
    Optional<MyBond> findByName(String name);
}
