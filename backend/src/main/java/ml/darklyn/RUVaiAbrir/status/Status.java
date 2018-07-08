package ml.darklyn.RUVaiAbrir.status;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ml.darklyn.RUVaiAbrir.enumeration.MealType;
import ml.darklyn.RUVaiAbrir.enumeration.RestaurantStatus;

@Entity
@Table(name="tb_status", uniqueConstraints={
	    @UniqueConstraint(columnNames = {"date", "mealType"})
	})
public class Status implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	
	@NotNull
	private LocalDate date;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private RestaurantStatus restaurantStatus;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private MealType mealType;
	
	public Status () { }

	public Status(@NotNull LocalDate localDate, @NotNull RestaurantStatus restaurantStatus, @NotNull MealType mealType) {
		super();
		this.date = localDate;
		this.restaurantStatus = restaurantStatus;
		this.mealType = mealType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public RestaurantStatus getRestaurantStatus() {
		return restaurantStatus;
	}

	public void setRestaurantStatus(RestaurantStatus restaurantStatus) {
		this.restaurantStatus = restaurantStatus;
	}

	public MealType getMealType() {
		return mealType;
	}

	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}
	
	
	
	

}
