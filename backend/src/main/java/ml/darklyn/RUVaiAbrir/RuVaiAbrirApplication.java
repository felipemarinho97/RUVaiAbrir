package ml.darklyn.RUVaiAbrir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RuVaiAbrirApplication {

	public static void main(String[] args) {
		SpringApplication.run(RuVaiAbrirApplication.class, args);
	}
}
