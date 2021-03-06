package ml.darklyn.RUVaiAbrir.status;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ml.darklyn.RUVaiAbrir.enumeration.MealType;

@Repository
public interface StatusRepository extends CrudRepository<Status, Long> {
	
	Boolean existsByDateAndMealType(LocalDate date, MealType mealType);
	
	Optional<Status> findOneByDateAndMealType(LocalDate date, MealType mealType);

}
