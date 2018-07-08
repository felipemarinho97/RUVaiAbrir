package ml.darklyn.RUVaiAbrir.menu;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ml.darklyn.RUVaiAbrir.enumeration.MealType;
import ml.darklyn.RUVaiAbrir.status.Status;

@Repository
public interface LunchMenuRepository extends CrudRepository<LunchMenu, Long> {
	
	Optional<LunchMenu> findOneByDateAndMealType(LocalDate date, MealType mealType);

}
