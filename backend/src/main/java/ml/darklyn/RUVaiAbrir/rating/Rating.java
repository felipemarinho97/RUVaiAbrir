package ml.darklyn.RUVaiAbrir.rating;

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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ml.darklyn.RUVaiAbrir.enumeration.MealType;
import ml.darklyn.RUVaiAbrir.user.User;

@Table(name="tb_rating", uniqueConstraints={
	    @UniqueConstraint(name="uq_rating_usr_dt_mt",
	    		columnNames = {"date", "mealType", "user_id"})
	})
@Entity
public class Rating implements Serializable {
	
	@JsonIgnore
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private LocalDate date;
	
	@NotNull(message="O tipo de refeição não pode ser nulo, talvez não seja horário apropriado?")
	@Enumerated(EnumType.STRING)
	private MealType mealType;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@NotNull
	private User user;
	
	@NotNull
	@Min(0)
	@Max(10)
	private Integer rating;
	
	public Rating() { }
	
	

	public Rating(@NotNull LocalDate date, @NotNull MealType mealType, @NotNull User user,
			@NotBlank @Min(0) @Max(10) Integer rating) {
		super();
		this.date = date;
		this.mealType = mealType;
		this.user = user;
		this.rating = rating;
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

	public MealType getMealType() {
		return mealType;
	}

	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
