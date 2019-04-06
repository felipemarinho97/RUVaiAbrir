package ml.darklyn.RUVaiAbrir;

import ml.darklyn.RUVaiAbrir.auth.AuthController;
import ml.darklyn.RUVaiAbrir.config.jwt.JwtTokenProvider;
import ml.darklyn.RUVaiAbrir.dto.JwtAuthResponse;
import ml.darklyn.RUVaiAbrir.dto.LoginRequest;
import ml.darklyn.RUVaiAbrir.dto.RegisterRequest;
import ml.darklyn.RUVaiAbrir.user.CustomUserDetailsService;
import ml.darklyn.RUVaiAbrir.user.User;
import ml.darklyn.RUVaiAbrir.user.UserPrincipal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Sql({"classpath:insert-roles.sql"})
@SpringBootTest
public class LoginTests {

    @Autowired
    private AuthController authController;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public void getValidRegister() {
        var registerRequest = new RegisterRequest();

        registerRequest.setEmail("login.test."+RegisterTests.VLADIMIR_LENIN_CCCP_GOV_SU);
        registerRequest.setFirstName(RegisterTests.VLADIMIR);
        registerRequest.setLastName(RegisterTests.ULYANOVSKY_LENIN);
        registerRequest.setUsername("login.test."+RegisterTests.ULYANOVSKY);
        registerRequest.setPassword(RegisterTests.VIDA_LONGA_AO_PROLETARIADO);

        try {
            var serverResponse = authController.registerUser(registerRequest);
            Assert.assertTrue(serverResponse.getStatusCode().equals(HttpStatus.CREATED));
        } catch (Exception e) {

        }
    }

    public UserPrincipal getUserFromJWT(String token) {
        Long userId = jwtTokenProvider.getUserIdFromJWT(token);
        return (UserPrincipal) customUserDetailsService.loadUserById(userId);
    }


    @Test
    public void contextLoads() {
    }

    @Test
    public void loginEmailTest() {
        getValidRegister();

        var loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("login.test."+RegisterTests.VLADIMIR_LENIN_CCCP_GOV_SU);
        loginRequest.setPassword(RegisterTests.VIDA_LONGA_AO_PROLETARIADO);

        var serverResponse = authController.authenticateUser(loginRequest);
        Assert.assertTrue(serverResponse.getBody() instanceof JwtAuthResponse);

        var jwtAuthResponse = ((JwtAuthResponse) serverResponse.getBody());
        var user = getUserFromJWT(jwtAuthResponse.getAccessToken());

        Assert.assertEquals("login.test."+RegisterTests.VLADIMIR_LENIN_CCCP_GOV_SU, user.getEmail());
    }

    @Before
    public void setUp() {
        getValidRegister();
    }

    @Test
    public void loginUsernameTest() {

        var loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("login.test."+RegisterTests.ULYANOVSKY);
        loginRequest.setPassword(RegisterTests.VIDA_LONGA_AO_PROLETARIADO);

        var serverResponse = authController.authenticateUser(loginRequest);
        Assert.assertTrue(serverResponse.getBody() instanceof JwtAuthResponse);

        var jwtAuthResponse = ((JwtAuthResponse) serverResponse.getBody());
        var user = getUserFromJWT(jwtAuthResponse.getAccessToken());

        Assert.assertEquals("login.test."+RegisterTests.ULYANOVSKY, user.getUsername());
    }


}
