package ml.darklyn.RUVaiAbrir.enumeration;

public enum MealType {
	LUNCH("Almoço"),
	DINNER("Janta");
	
	private String customName;
	
	MealType(String customName) {
		this.customName = customName;
	}

	public String getCustomName() {
		return customName;
	}
}
