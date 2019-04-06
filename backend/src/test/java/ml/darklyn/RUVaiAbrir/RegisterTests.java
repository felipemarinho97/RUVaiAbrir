package ml.darklyn.RUVaiAbrir;

import ml.darklyn.RUVaiAbrir.auth.AuthController;
import ml.darklyn.RUVaiAbrir.dto.RegisterRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Sql({"classpath:insert-roles.sql"})
@SpringBootTest
public class RegisterTests {


    public static final String VIDA_LONGA_AO_PROLETARIADO = "vida.longa.ao.proletariado";
    public static final String ULYANOVSKY = "u1lyanovsky";
    public static final String ULYANOVSKY_LENIN = "Ulyanovsky 'Lenin'";
    public static final String VLADIMIR = "Vladimir";
    public static final String VLADIMIR_LENIN_CCCP_GOV_SU = "vladimir.lenin@cccp.gov.su";

    @Autowired
    private AuthController authController;

    @Test
    public void contextLoads() {
    }


    public RegisterRequest getValidRegister() {
        var registerRequest = new RegisterRequest();

        registerRequest.setEmail(VLADIMIR_LENIN_CCCP_GOV_SU);
        registerRequest.setFirstName(VLADIMIR);
        registerRequest.setLastName(ULYANOVSKY_LENIN);
        registerRequest.setUsername(ULYANOVSKY);
        registerRequest.setPassword(VIDA_LONGA_AO_PROLETARIADO);

        return registerRequest;
    }

    @Test
    public void registerRequestTest() {
        var validRegisterRequest = getValidRegister();

        var serverResponse = authController.registerUser(validRegisterRequest);
        Assert.assertTrue(serverResponse.getStatusCode().equals(HttpStatus.CREATED));
    }

}
