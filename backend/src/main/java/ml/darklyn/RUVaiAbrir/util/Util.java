package ml.darklyn.RUVaiAbrir.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ml.darklyn.RUVaiAbrir.comments.Comment;
import ml.darklyn.RUVaiAbrir.exceptions.BadRequestException;
import ml.darklyn.RUVaiAbrir.rating.Rating;
import ml.darklyn.RUVaiAbrir.rating.RatingService;

@Component
public class Util {
	
	@Autowired
	private RatingService ratingService;
	
	public void applyIncludeParams(List<String> include, final Comment comment) {
		include.forEach((toInclude) -> {
			switch (toInclude) {
				case "classificacao":
					applyRatingParam(comment);
					break;
				default:
					throw new BadRequestException("O atributo '"+toInclude+"' passado para o parâmetro 'inc' é inválido.");
			}
		});
	}

	private void applyRatingParam(final Comment comment) {
		try {
			Rating rating = ratingService.getRating(comment.getUser(), comment.getDate(), comment.getMealType());
			comment.setRating(rating.getRating());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
