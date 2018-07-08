package ml.darklyn.RUVaiAbrir.util;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ml.darklyn.RUVaiAbrir.enumeration.IncludeOptions;
import ml.darklyn.RUVaiAbrir.enumeration.MealType;
import ml.darklyn.RUVaiAbrir.exceptions.BadRequestException;
import ml.darklyn.RUVaiAbrir.rating.Rating;
import ml.darklyn.RUVaiAbrir.rating.RatingService;
import ml.darklyn.RUVaiAbrir.status.StatusService;
import ml.darklyn.RUVaiAbrir.status.UserStatus;
import ml.darklyn.RUVaiAbrir.user.User;

@Component
public class EntityIncluder<T> {
	
	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private StatusService statusService;
	
	public void applyIncludeParams(List<String> include, final T entity) {
		include.forEach((toInclude) -> {
			IncludeOptions ofRestName = Optional.ofNullable(IncludeOptions.ofRestName(toInclude))
				.orElseThrow(
					() -> new BadRequestException("O atributo '"+toInclude+"' passado para o parâmetro 'inc' é inválido."
								+ " Atributos disponíveis: " + IncludeOptions.avaliableRestNames()));
			
			switch (ofRestName) {
				case RATING:
					applyRatingParam(entity);
					break;
				case USER_STATUS:
					applyUserStatusParam(entity);
			}
		});
	}

	private void applyRatingParam(final T entity) {
		try {
			Method[] methods = entity.getClass().getMethods();
			
			User user = null;
			LocalDate date = null;
			MealType mealType = null;
			Method setRating = null;
			
			for (Method method : methods) {
				switch (method.getName()) {
				case "getUser":
					user = (User) method.invoke(entity);
					break;
				case "getDate":
					date = (LocalDate) method.invoke(entity);
					break;
				case "getMealType":
					mealType = (MealType) method.invoke(entity);
					break;
				case "setRating":
					setRating = method;
					break;
				}
			}
			
			validate(user, date, mealType, setRating);
			
			Rating rating = ratingService.getRating(user, date, mealType);
			setRating.invoke(entity, rating.getRating());
		} catch (Exception e) {
			System.err.println(e.getMessage());
			incorrectIncludeAttr(IncludeOptions.RATING, entity);
		}
	}
	
	private void validate(Object... objects) {
		for (Object object : objects) {
			if (object == null)
				throw new NullPointerException("O atributo não pode ser nulo.");
		}
	}
	
	private void applyUserStatusParam(final T entity) {
		try {
			Method[] methods = entity.getClass().getMethods();
			
			User user = null;
			LocalDate date = null;
			MealType mealType = null;
			Method setUserStatus = null;
			
			for (Method method : methods) {
				switch (method.getName()) {
				case "getUser":
					user = (User) method.invoke(entity);
					break;
				case "getDate":
					date = (LocalDate) method.invoke(entity);
					break;
				case "getMealType":
					mealType = (MealType) method.invoke(entity);
					break;
				case "setUserStatus":
					setUserStatus = method;
					break;
				}
			}
			
			validate(user, date, mealType, setUserStatus);
			
			UserStatus userStatus = statusService.getUserStatus(user, date, mealType);
			setUserStatus.invoke(entity, userStatus);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			incorrectIncludeAttr(IncludeOptions.USER_STATUS, entity);
		}
	}

	private static void incorrectIncludeAttr(IncludeOptions attr, Object entity) {
		throw new BadRequestException("O atributo '" + attr.getRestName() + "' não é aplicável para a entidade '" + entity.getClass().getSimpleName() + "'");
	}

}
