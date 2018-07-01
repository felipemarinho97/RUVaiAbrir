package ml.darklyn.RUVaiAbrir.enumeration;

public enum RestaurantStatus {
	OPENED("Aberto"),
	CLOSED("Fechado");
	
	private String customName;
	
	RestaurantStatus(String customName) {
		this.customName = customName;
	}

	public String getCustomName() {
		return customName;
	}
	
}
