package ml.darklyn.RUVaiAbrir.user;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ml.darklyn.RUVaiAbrir.config.JdbcRepository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByUsernameOrEmail(String username, String email);

	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);

}
