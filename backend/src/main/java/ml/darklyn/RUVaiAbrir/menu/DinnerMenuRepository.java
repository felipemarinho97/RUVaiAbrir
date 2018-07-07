package ml.darklyn.RUVaiAbrir.menu;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ml.darklyn.RUVaiAbrir.enumeration.MealType;

public interface DinnerMenuRepository extends CrudRepository<DinnerMenu, Long> {
	
	Optional<DinnerMenu> findOneByDateAndMealType(LocalDate date, MealType mealType);

}
