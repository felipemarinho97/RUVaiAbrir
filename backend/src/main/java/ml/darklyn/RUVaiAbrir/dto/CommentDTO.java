package ml.darklyn.RUVaiAbrir.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ml.darklyn.RUVaiAbrir.comments.Comment;
import ml.darklyn.RUVaiAbrir.enumeration.MealType;
import ml.darklyn.RUVaiAbrir.status.UserStatus;
import ml.darklyn.RUVaiAbrir.user.User;

@JsonInclude(Include.NON_NULL)
public class CommentDTO implements Serializable {
	
	private Long id;
	
	private User user;
	
	@NotBlank
	private String message;
	
	@NotNull
	private LocalDate date;
	
	@NotNull(message="O tipo de refeição não pode ser nulo, talvez não seja horário apropriado?")
	@Enumerated(EnumType.STRING)
	private MealType mealType;
	
	@Transient
	private Integer rating;
	
	@Transient
	private UserStatus userStatus;
	
	public CommentDTO()	{  }

	public CommentDTO(Comment comment) {
		this.id = comment.getId();
		this.user = comment.getUser();
		this.message = comment.getMessage();
		this.date = comment.getDate();
		this.mealType = comment.getMealType();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
	
	

}
