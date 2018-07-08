package ml.darklyn.RUVaiAbrir.rating;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ml.darklyn.RUVaiAbrir.enumeration.MealType;
import ml.darklyn.RUVaiAbrir.menu.DinnerMenu;
import ml.darklyn.RUVaiAbrir.user.User;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Long> {
	
	@Query("SELECT AVG(tb_rating.rating)"
			+ "FROM Rating tb_rating "
			+ "WHERE mealType = ?2 AND date = ?1 ")
	Integer getAverageRatingByDateAndMealType(LocalDate date, MealType mealType);
	
	Optional<Rating> findByUserAndDateAndMealType(User user, LocalDate date, MealType mealType);


}
