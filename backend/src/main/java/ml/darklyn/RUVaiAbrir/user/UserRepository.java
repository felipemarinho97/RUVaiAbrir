package ml.darklyn.RUVaiAbrir.user;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByUsernameOrEmail(String username, String email);

	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);

}
