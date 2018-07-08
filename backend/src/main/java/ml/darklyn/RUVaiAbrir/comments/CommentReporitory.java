package ml.darklyn.RUVaiAbrir.comments;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ml.darklyn.RUVaiAbrir.enumeration.MealType;;

@Repository
public interface CommentReporitory extends CrudRepository<Comment, Long> {
	
	Optional<Page<Comment>> findByDateAndMealType(LocalDate date, MealType mealType, Pageable pageable);

}
