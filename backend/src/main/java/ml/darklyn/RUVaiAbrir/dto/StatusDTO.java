package ml.darklyn.RUVaiAbrir.dto;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import ml.darklyn.RUVaiAbrir.enumeration.RestaurantStatus;

public class StatusDTO {
	
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
