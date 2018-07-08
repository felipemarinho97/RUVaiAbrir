package ml.darklyn.RUVaiAbrir.menu;

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
@Table(name="tb_lunch_menu", uniqueConstraints={
	    @UniqueConstraint(columnNames = {"date", "mealType"})
	})
public class LunchMenu {
	
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
	private String mainDish;

	@Size(min = 2)
	private String vegetarianDish;
	
	@NotBlank
	@Size(min = 2)
	private String guarnish;
	
	@Size(min = 2)
	private String salad;
	
	@Size(min = 2)
	private String dessert;
	
	@Size(min = 2)
	private String drink;
	
	@Size(min = 2)
	private String fruit;
	
	public LunchMenu() { }

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

	public String getGuarnish() {
		return guarnish;
	}

	public void setGuarnish(String guarnish) {
		this.guarnish = guarnish;
	}

	public String getSalad() {
		return salad;
	}

	public void setSalad(String salad) {
		this.salad = salad;
	}

	public String getDessert() {
		return dessert;
	}

	public void setDessert(String dessert) {
		this.dessert = dessert;
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

}
