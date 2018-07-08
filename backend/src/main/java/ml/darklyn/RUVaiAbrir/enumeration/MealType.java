package ml.darklyn.RUVaiAbrir.enumeration;

public enum MealType {
	LUNCH("Almoço", "almoco"),
	DINNER("Janta", "janta");
	
	private String customName;
	
	private String paramName;
	
	MealType(String customName, String paramName) {
		this.customName = customName;
		this.paramName = paramName;
	}

	public String getCustomName() {
		return customName;
	}

	public String getParamName() {
		return paramName;
	}
	
}
