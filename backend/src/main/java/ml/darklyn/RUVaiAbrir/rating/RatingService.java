package ml.darklyn.RUVaiAbrir.rating;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ml.darklyn.RUVaiAbrir.dto.RatingDTO;
import ml.darklyn.RUVaiAbrir.enumeration.MealType;
import ml.darklyn.RUVaiAbrir.exceptions.NotFoundException;
import ml.darklyn.RUVaiAbrir.time.TimeService;
import ml.darklyn.RUVaiAbrir.user.User;
import ml.darklyn.RUVaiAbrir.user.UserRepository;
import ml.darklyn.RUVaiAbrir.util.AuthValidator;

@Service
public class RatingService {
	
	@Autowired
	private RatingRepository ratingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TimeService timeSevice;
	
	public RatingDTO getGeneralRating() {
		LocalDate currentDate = timeSevice.getCurrentDate();
		MealType currentMealType = timeSevice.getCurrentMealType();
		Integer averageRatingByDateAndMealType = ratingRepository.getAverageRatingByDateAndMealType(currentDate, currentMealType);
		
		return new RatingDTO(averageRatingByDateAndMealType);
	}


	public Rating createRating(@Valid RatingDTO ratingDTO, String username, String email) {
		User user = userRepository.findByUsernameOrEmail(username, email)
				.get();
		LocalDate currentDate = timeSevice.getCurrentDate();
		MealType currentMealType = timeSevice.getCurrentMealType();
		
		Rating rating = new Rating(currentDate, currentMealType, user, ratingDTO.getRating());
		
		return ratingRepository.save(rating);
	}


	public Rating getRatingById(Long id) {
		Rating rating = ratingRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Não foi encontrado nenhuma classificação com o ID especificado."));
		
		return rating;
	}


	public Rating updateRating(Long id, RatingDTO updatedRating, Long userId) {
		Rating rating = getRatingById(id);
		
		AuthValidator.validate(rating, userId);
		
		rating.setRating(updatedRating.getRating());
		
		return ratingRepository.save(rating);
	}


	public void deleteRating(Long id, Long userId) {
		Rating rating = getRatingById(id);
		
		AuthValidator.validate(rating, userId);
		
		ratingRepository.delete(rating);
	}

	public Rating getRating(User user, LocalDate date, MealType mealType) {
		return ratingRepository.findByUserAndDateAndMealType(user, date, mealType)
				.orElseThrow(() -> new NotFoundException("Não foi encontrado nenhuma classificação para o Usuário especificado."));
	}

}
