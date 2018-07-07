package ml.darklyn.RUVaiAbrir.menu;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ml.darklyn.RUVaiAbrir.dto.MenuDTO;
import ml.darklyn.RUVaiAbrir.enumeration.MealType;
import ml.darklyn.RUVaiAbrir.time.TimeService;


@RequestMapping("/cardapio")
@RestController
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private TimeService timeService;
	
	@PreAuthorize("hasHole('EMPLOYEE')")
	@PutMapping(params = { "tipo" })
	public ResponseEntity<?> addOrUpdateLunchMenu(
			@RequestBody @Valid MenuDTO menu, 
			@RequestParam(name="tipo",required=true) String type) {
		ResponseEntity<?> response = new ResponseEntity(HttpStatus.BAD_REQUEST);
		
		if (type.equalsIgnoreCase(MealType.LUNCH.getParamName())) {
			LunchMenu lunchMenu = menuService.addOrUpdateLunch(menu.getLunchMenu());
			response = ResponseEntity.ok(lunchMenu);
		} else if (type.equalsIgnoreCase(MealType.DINNER.getParamName())) {
			DinnerMenu dinnerMenu = menuService.addOrUpdateDinner(menu.getDinnerMenu());
			response = ResponseEntity.ok(dinnerMenu);
		}
		
		return response;
	}
	
	@GetMapping
	public ResponseEntity<?> getCurrentMenu() {
		switch (timeService.getCurrentMealType()) {
		case DINNER:
			return ResponseEntity.ok(menuService.getCurrentDinnerMenu());
		case LUNCH:
			return ResponseEntity.ok(menuService.getCurrentLunchMenu());
		}
		return ResponseEntity.notFound().build();
	}

}
