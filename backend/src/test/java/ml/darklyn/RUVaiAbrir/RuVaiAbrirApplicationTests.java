package ml.darklyn.RUVaiAbrir;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import ml.darklyn.RUVaiAbrir.auth.AuthController;
import ml.darklyn.RUVaiAbrir.dto.JwtAuthResponse;
import ml.darklyn.RUVaiAbrir.dto.LoginRequest;
import ml.darklyn.RUVaiAbrir.dto.RegisterRequest;


@RunWith(SpringRunner.class)
@Sql({"classpath:insert-roles.sql"})
@SpringBootTest
public class RuVaiAbrirApplicationTests {

	@Test
	public void contextLoads() {
	}

}
