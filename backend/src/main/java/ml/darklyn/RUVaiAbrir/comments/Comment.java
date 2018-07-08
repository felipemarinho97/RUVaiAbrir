package ml.darklyn.RUVaiAbrir.comments;

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
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ml.darklyn.RUVaiAbrir.enumeration.MealType;
import ml.darklyn.RUVaiAbrir.user.User;

@Table(name = "tb_comment")
@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne
	private User user;
	
	@NotBlank
	private String message;
	
	@NotNull
	private LocalDate date;
	
	@NotNull(message="O tipo de refeição não pode ser nulo, talvez não seja horário apropriado?")
	@Enumerated(EnumType.STRING)
	private MealType mealType;
	
	@JsonInclude(Include.NON_NULL)
	@Transient
	private Integer rating;
	
	public Comment() { }
	
	public Comment(@NotNull User user, @NotBlank String message, @NotNull LocalDate date,
			@NotNull(message = "O tipo de refeição não pode ser nulo, talvez não seja horário apropriado?") MealType mealType) {
		super();
		this.user = user;
		this.message = message;
		this.date = date;
		this.mealType = mealType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(@Valid String message) {
		this.message = message;
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

}
