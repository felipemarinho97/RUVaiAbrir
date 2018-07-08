package ml.darklyn.RUVaiAbrir.util;

import ml.darklyn.RUVaiAbrir.comments.Comment;
import ml.darklyn.RUVaiAbrir.exceptions.UnauthorizedException;
import ml.darklyn.RUVaiAbrir.rating.Rating;

public class AuthValidator {
	
	private static final String UNAUTHORIZED_MESSAGE = "Você não tem autorização para modificar este recurso.";

	public static void validate(Comment entity, Long userId) {
		if (!entity.getUser().getId().equals(userId)) {
			throw new UnauthorizedException(UNAUTHORIZED_MESSAGE);
		}
	}
	
	public static void validate(Rating entity, Long userId) {
		if (!entity.getUser().getId().equals(userId)) {
			throw new UnauthorizedException(UNAUTHORIZED_MESSAGE);
		}
	}

}
