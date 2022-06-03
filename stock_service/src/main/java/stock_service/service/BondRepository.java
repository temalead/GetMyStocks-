package stock_service.service;


import org.springframework.data.repository.CrudRepository;
import stock_service.entity.MyBond;

import java.util.Optional;

public interface BondRepository extends CrudRepository<MyBond, String> {
    Optional<MyBond> findByName(String name);
}
