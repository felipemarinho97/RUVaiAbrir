package ml.darklyn.RUVaiAbrir.enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum IncludeOptions {
	RATING("classificacao"),
	USER_STATUS("status-usuario");
	
	IncludeOptions(String restName) {
		this.restName = restName;
	}
	
	private String restName;

	public final String getRestName() {
		return restName;
	}
	
	public static IncludeOptions ofRestName(String restName) {
		IncludeOptions result = null;
		for (IncludeOptions includeOption : IncludeOptions.values()) {
			if (includeOption.getRestName().equals(restName)) {
				result = includeOption;
				break;
			}
		}
		return result;		
	}

	public static List<String> avaliableRestNames() {
		List<String> avaliableOptions = new ArrayList<>();
		Arrays.asList(IncludeOptions.values())
					.stream().map(IncludeOptions::getRestName)
					.forEach(avaliableOptions::add);
		return avaliableOptions;
	}
	
}
