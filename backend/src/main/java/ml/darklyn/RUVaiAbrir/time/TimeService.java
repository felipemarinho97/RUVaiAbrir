package ml.darklyn.RUVaiAbrir.time;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import ml.darklyn.RUVaiAbrir.enumeration.MealType;

@Service
public class TimeService {
	
	private ZoneId zoneId = ZoneId.of("America/Recife");
	
	private static final LocalTime lunchMin = LocalTime.of(10, 0);
	private static final LocalTime lunchMax = LocalTime.of(15, 0);
	
	private static final LocalTime dinnerMin = LocalTime.of(17, 0);
	private static final LocalTime dinnerMax = LocalTime.of(20, 0);

	public ZonedDateTime getCurrentDateTime() {
		return ZonedDateTime.now(zoneId);
	}
	
	public LocalDate getCurrentDate() {
		return getCurrentDateTime().toLocalDate();
	}
	
	public LocalTime getCurrentTime() {
		return getCurrentDateTime().toLocalTime();
	}

	public MealType getCurrentMealType() {
		return this.getCurrentMealType(this.getCurrentDateTime());
	}

	private MealType getCurrentMealType(ZonedDateTime currentTime) {
		MealType mealType = null;
		LocalTime localTime = currentTime.toLocalTime();
		
		if (isLunchTime(localTime)) {
			mealType = MealType.LUNCH;
		} else if (isDinnerTime(localTime)) {
			mealType = MealType.DINNER;
		}
		
		return mealType;
	}
	
	private Boolean isLunchTime(LocalTime localTime) {
		return localTime.isAfter(lunchMin) && localTime.isBefore(lunchMax);
	}
	
	private Boolean isDinnerTime(LocalTime localTime) {
		return localTime.isAfter(dinnerMin) && localTime.isBefore(dinnerMax);
	}
	

}
