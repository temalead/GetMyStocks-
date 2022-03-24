package bot.repository;

import bot.entity.MyBond;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BondRepository extends CrudRepository<MyBond, String> {
    Optional<MyBond> findByName(String name);
}
