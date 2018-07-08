package ml.darklyn.RUVaiAbrir.menu;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ml.darklyn.RUVaiAbrir.enumeration.MealType;

@Repository
public interface DinnerMenuRepository extends CrudRepository<DinnerMenu, Long> {
	
	Optional<DinnerMenu> findOneByDateAndMealType(LocalDate date, MealType mealType);

}
