package ml.darklyn.RUVaiAbrir.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ml.darklyn.RUVaiAbrir.comments.Comment;
import ml.darklyn.RUVaiAbrir.exceptions.BadRequestException;
import ml.darklyn.RUVaiAbrir.rating.Rating;
import ml.darklyn.RUVaiAbrir.rating.RatingService;
import ml.darklyn.RUVaiAbrir.status.StatusService;
import ml.darklyn.RUVaiAbrir.status.UserStatus;

@Component
public class Util {
	
	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private StatusService statusService;
	
	public void applyIncludeParams(List<String> include, final Comment comment) {
		include.forEach((toInclude) -> {
			switch (toInclude) {
				case "classificacao":
					applyRatingParam(comment);
					break;
				case "status":
					applyUserStatusParam(comment);
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
	
	private void applyUserStatusParam(final Comment comment) {
		try {
			UserStatus userStatus = statusService.getUserStatus(comment.getUser(), comment.getDate(), comment.getMealType());
			comment.setUserStatus(userStatus);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
