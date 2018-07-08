package ml.darklyn.RUVaiAbrir.dto;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import ml.darklyn.RUVaiAbrir.enumeration.RestaurantStatus;

public class StatusDTO implements Serializable {
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private RestaurantStatus restaurantStatus;
	
	public StatusDTO() { }

	public RestaurantStatus getRestaurantStatus() {
		return restaurantStatus;
	}

	public void setRestaurantStatus(RestaurantStatus restaurantStatus) {
		this.restaurantStatus = restaurantStatus;
	}
	
	

}
