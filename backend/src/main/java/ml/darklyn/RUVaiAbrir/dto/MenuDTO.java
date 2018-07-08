package ml.darklyn.RUVaiAbrir.dto;

import java.io.Serializable;

import javax.validation.Valid;

import ml.darklyn.RUVaiAbrir.menu.DinnerMenu;
import ml.darklyn.RUVaiAbrir.menu.LunchMenu;

public class MenuDTO implements Serializable {
	
	@Valid
	private LunchMenu lunchMenu;
	
	@Valid
	private DinnerMenu dinnerMenu;
	
	public MenuDTO() { }

	public LunchMenu getLunchMenu() {
		return lunchMenu;
	}

	public void setLunchMenu(LunchMenu lunchMenu) {
		this.lunchMenu = lunchMenu;
	}

	public DinnerMenu getDinnerMenu() {
		return dinnerMenu;
	}

	public void setDinnerMenu(DinnerMenu dinnerMenu) {
		this.dinnerMenu = dinnerMenu;
	}
	
	

}
