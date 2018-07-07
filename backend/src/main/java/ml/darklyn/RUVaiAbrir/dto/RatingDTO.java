package ml.darklyn.RUVaiAbrir.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RatingDTO {
	
	@NotNull
	@Min(0)
	@Max(10)
	private Integer rating;

	public RatingDTO() {
		super();
	}

	public RatingDTO(Integer rating) {
		this.setRating(rating);
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}
	
	

}
