package ml.darklyn.RUVaiAbrir;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import ml.darklyn.RUVaiAbrir.auth.AuthController;
import ml.darklyn.RUVaiAbrir.dto.JwtAuthResponse;
import ml.darklyn.RUVaiAbrir.dto.LoginRequest;
import ml.darklyn.RUVaiAbrir.dto.RegisterRequest;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RuVaiAbrirApplicationTests {
	
	@Autowired
	private AuthController authController;

	@Test
	public void contextLoads() {
	}
	
	public RegisterRequest getValidRegister() {
		var registerRequest = new RegisterRequest();
		
		registerRequest.setEmail("vladimir.lenin@cccp.gov.su");
		registerRequest.setFirstName("Vladimir");
		registerRequest.setLastName("Ulyanovsky 'Lenin'");
		registerRequest.setUsername("ulyanovsky");
		registerRequest.setPassword("vida.longa.ao.proletariado");
		
		return registerRequest;
	}
	
	@Test
	public void registerRequestTest() {
		var validRegisterRequest = getValidRegister();
		
		var serverResponse = authController.registerUser(validRegisterRequest);
		Assert.assertTrue(serverResponse.getStatusCode().equals(HttpStatus.CREATED));
	}
	
	@Test
	public void loginTest() {
		var loginRequest = new LoginRequest();
		loginRequest.setUsernameOrEmail("felipevm97@gmail.com");
		loginRequest.setPassword("felipefoda");
		
		var jwtAuthResponse = authController.authenticateUser(loginRequest);
		Assert.assertTrue(jwtAuthResponse.getBody() instanceof JwtAuthResponse);
	}

}
