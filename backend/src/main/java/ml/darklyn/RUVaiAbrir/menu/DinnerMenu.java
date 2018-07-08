package ml.darklyn.RUVaiAbrir.menu;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ml.darklyn.RUVaiAbrir.enumeration.MealType;

@Entity
@Table(name="tb_dinner_menu", uniqueConstraints={
	    @UniqueConstraint(columnNames = {"date", "mealType"})
	})
public class DinnerMenu implements Serializable {
	
	@JsonIgnore
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private LocalDate date;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private MealType mealType;
	
	@NotBlank
	@Size(min = 2)
	private String entree;
	
	@NotBlank
	@Size(min = 2)
	private String mainDish;

	@Size(min = 2)
	private String vegetarianDish;
	
	@Size(min = 2)
	private String drink;
	
	@Size(min = 2)
	private String fruit;
	
	@Size(min = 2)
	private String guarnish;
	
	public DinnerMenu() { }

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

	public String getEntree() {
		return entree;
	}

	public void setEntree(String entree) {
		this.entree = entree;
	}

	public String getMainDish() {
		return mainDish;
	}

	public void setMainDish(String mainDish) {
		this.mainDish = mainDish;
	}

	public String getVegetarianDish() {
		return vegetarianDish;
	}

	public void setVegetarianDish(String vegetarianDish) {
		this.vegetarianDish = vegetarianDish;
	}

	public String getDrink() {
		return drink;
	}

	public void setDrink(String drink) {
		this.drink = drink;
	}

	public String getFruit() {
		return fruit;
	}

	public void setFruit(String fruit) {
		this.fruit = fruit;
	}

	public String getGuarnish() {
		return guarnish;
	}

	public void setGuarnish(String guarnish) {
		this.guarnish = guarnish;
	}
	
	


}
