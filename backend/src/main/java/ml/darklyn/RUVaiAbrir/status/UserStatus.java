package ml.darklyn.RUVaiAbrir.status;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import ml.darklyn.RUVaiAbrir.enumeration.MealType;
import ml.darklyn.RUVaiAbrir.enumeration.RestaurantStatus;
import ml.darklyn.RUVaiAbrir.user.User;

@Entity
@Table(name="tb_user_status", uniqueConstraints={
	    @UniqueConstraint(columnNames = {"date", "mealType", "user_id"})
	})
public class UserStatus implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private LocalDate date;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private RestaurantStatus restaurantStatus;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private MealType mealType;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	private User user;

	public UserStatus(@NotNull LocalDate date, @NotNull RestaurantStatus restaurantStatus, @NotNull MealType mealType, @NotNull User user) {
		super();
		this.date = date;
		this.restaurantStatus = restaurantStatus;
		this.mealType = mealType;
		this.user = user;
	}
	
	public UserStatus() { }

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	


}
