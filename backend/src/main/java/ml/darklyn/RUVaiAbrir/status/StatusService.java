package ml.darklyn.RUVaiAbrir.status;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ml.darklyn.RUVaiAbrir.dto.StatusDTO;
import ml.darklyn.RUVaiAbrir.enumeration.MealType;
import ml.darklyn.RUVaiAbrir.enumeration.RestaurantStatus;
import ml.darklyn.RUVaiAbrir.exceptions.NotFoundException;
import ml.darklyn.RUVaiAbrir.exceptions.UnauthorizedException;
import ml.darklyn.RUVaiAbrir.rating.Rating;
import ml.darklyn.RUVaiAbrir.time.TimeService;
import ml.darklyn.RUVaiAbrir.user.User;
import ml.darklyn.RUVaiAbrir.user.UserRepository;

@Service
public class StatusService {
	
	@Autowired
	private StatusRepository statusRepository;
	
	@Autowired
	private UserStatusRepository userStatusRepository;
	
	@Autowired
	private TimeService timeService;

	@Autowired
	private UserRepository userRepository;

	public Status addOrUpdateStatus(StatusDTO statusDTO) {
		LocalDate date = timeService.getCurrentDate();
		MealType mealType = timeService.getCurrentMealType();
		
		Status status = statusRepository.findOneByDateAndMealType(date, mealType)
				.orElse(new Status(date, statusDTO.getRestaurantStatus(), mealType));
		
		status.setRestaurantStatus(statusDTO.getRestaurantStatus());
		
		return statusRepository.save(status);
	}

	public Status getCurrentStatus() {
		LocalDate date = timeService.getCurrentDate();
		MealType mealType = timeService.getCurrentMealType();
		
		Status status = statusRepository.findOneByDateAndMealType(date, mealType)
				.orElseThrow(() -> new NotFoundException("Não foi encontrado nenhum status oficial para o restaurante."));
		
		return status;
	}
	
	public Page<UserStatus> getAllCurrentUserStatus(Pageable pageable) {
		LocalDate date = timeService.getCurrentDate();
		MealType mealType = timeService.getCurrentMealType();

		Page<UserStatus> userStatusPage = userStatusRepository.findByDateAndMealType(date, mealType, pageable)
				.orElseThrow(() -> new NotFoundException("Não foi defindo nenhum status por usuários para o restaurante."));

		return userStatusPage;
	}

	public Status getCurrentUserStatus() {
		LocalDate date = timeService.getCurrentDate();
		MealType mealType = timeService.getCurrentMealType();
		var averageStatus = new Status(date, RestaurantStatus.CLOSED, mealType);
		
		List<UserStatus> userStatusList = userStatusRepository.findByDateAndMealType(date, mealType)
				.orElseThrow(() -> new NotFoundException("Não foi defindo nenhum status por usuários para o restaurante."));
		
		final AtomicInteger closedQtd = new AtomicInteger(0);
		final AtomicInteger openedQtd = new AtomicInteger(0);
		
		userStatusList.stream().forEach((status) -> {

			switch (status.getRestaurantStatus()) {
				case CLOSED:
					closedQtd.incrementAndGet();
					break;
				case OPENED:
					openedQtd.incrementAndGet();
					break;
				}
		});
		
		if (closedQtd.get() >= openedQtd.get()) {
			averageStatus.setRestaurantStatus(RestaurantStatus.CLOSED);			
		} else {
			averageStatus.setRestaurantStatus(RestaurantStatus.OPENED);			
		}
		
		
		return averageStatus;
	}

	public UserStatus createUserStatus(StatusDTO statusDTO, String email) {
		LocalDate date = timeService.getCurrentDate();
		MealType mealType = timeService.getCurrentMealType();
		User user = userRepository.findByUsernameOrEmail(email, email).get();
		RestaurantStatus restaurantStatus = statusDTO.getRestaurantStatus();
		
		UserStatus userStatus = new UserStatus(date, restaurantStatus, mealType, user);
				
		return userStatusRepository.save(userStatus);
	}

	public UserStatus getUserStatusById(Long id) {
		UserStatus userStatus = userStatusRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Não foi encontrado nenhum status com o ID especificado."));
		
		return userStatus;
	}

	public UserStatus updateUserStatus(Long id, StatusDTO statusDTO) {
		UserStatus userStatus = userStatusRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Não foi encontrado nenhum status com o ID especificado."));
		
		userStatus.setRestaurantStatus(statusDTO.getRestaurantStatus());
		
		return userStatusRepository.save(userStatus);
	}

	public void removeUserStatus(Long id, String email) {
		UserStatus userStatus = userStatusRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Não foi encontrado nenhum status com o ID especificado."));
		
		if (userStatus.getUser().getEmail().equals(email)) {
			userStatusRepository.delete(userStatus);
		} else {
			throw new UnauthorizedException("Desculpe, você não tem autorização para acessar este recurso.");
		}

	}

	public UserStatus getUserStatus(User user, LocalDate date, MealType mealType) {
		return userStatusRepository.findOneByUserAndDateAndMealType(user, date, mealType)
				.orElseThrow(() -> new NotFoundException("Não foi encontrado nenhuma Status para o Usuário especificado."));
	}

}
