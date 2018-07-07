package ml.darklyn.RUVaiAbrir.menu;

import java.time.LocalDate;
import java.util.function.Supplier;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ml.darklyn.RUVaiAbrir.enumeration.MealType;
import ml.darklyn.RUVaiAbrir.exceptions.NotFoundException;
import ml.darklyn.RUVaiAbrir.time.TimeService;

@Service
public class MenuService {
	
	@Autowired
	private LunchMenuRepository lunchMenuRepository;
	
	@Autowired
	private DinnerMenuRepository dinnerMenuRepository;
	
	@Autowired
	private TimeService timeService;

	public LunchMenu addOrUpdateLunch(@Valid LunchMenu menu) {
		LunchMenu myMenu = lunchMenuRepository.findOneByDateAndMealType(menu.getDate(), menu.getMealType()).get();
		
		if (myMenu != null) {
			menu.setId(myMenu.getId());
		}
		
		return lunchMenuRepository.save(menu);
	}

	public DinnerMenu addOrUpdateDinner(DinnerMenu menu) {
		DinnerMenu myMenu = dinnerMenuRepository.findOneByDateAndMealType(menu.getDate(), menu.getMealType()).get();
		
		if (myMenu != null) {
			menu.setId(myMenu.getId());
		}
		
		return dinnerMenuRepository.save(menu);
	}

	public DinnerMenu getCurrentDinnerMenu() {
		LocalDate currentDate = timeService.getCurrentDate();
		MealType currentMealType = timeService.getCurrentMealType();
		DinnerMenu dinnerMenu = dinnerMenuRepository.findOneByDateAndMealType(currentDate, currentMealType)
			.orElseThrow(() -> new NotFoundException("Não foi encontrado nenhum cardápio para a janta de hoje."));
		
		return dinnerMenu;
	}

	public LunchMenu getCurrentLunchMenu() {
		LocalDate currentDate = timeService.getCurrentDate();
		MealType currentMealType = timeService.getCurrentMealType();
		LunchMenu lunchMenu = lunchMenuRepository.findOneByDateAndMealType(currentDate, currentMealType)
			.orElseThrow(() -> new NotFoundException("Não foi encontrado nenhum cardápio para o almoço de hoje."));

		return lunchMenu;
	}

}
