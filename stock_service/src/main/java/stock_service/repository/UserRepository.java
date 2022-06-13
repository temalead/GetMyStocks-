package stock_service.repository;


import org.springframework.data.repository.CrudRepository;
import stock_service.entity.User;

public interface UserRepository extends CrudRepository<User, String> {

}
