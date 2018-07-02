package ml.darklyn.RUVaiAbrir.status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ml.darklyn.RUVaiAbrir.enumeration.MealType;

public interface UserStatusRepository extends CrudRepository<UserStatus, Long> {
	
	Optional<List<UserStatus>> findByDateAndMealType(LocalDate date, MealType mealType);

}
