package ml.darklyn.RUVaiAbrir;

import com.fasterxml.jackson.databind.ObjectMapper;
import ml.darklyn.RUVaiAbrir.config.jwt.JwtTokenProvider;
import ml.darklyn.RUVaiAbrir.dto.StatusDTO;
import ml.darklyn.RUVaiAbrir.enumeration.RestaurantStatus;
import ml.darklyn.RUVaiAbrir.enumeration.RoleName;
import ml.darklyn.RUVaiAbrir.status.StatusController;
import ml.darklyn.RUVaiAbrir.status.StatusRepository;
import ml.darklyn.RUVaiAbrir.status.StatusService;
import ml.darklyn.RUVaiAbrir.status.UserStatusRepository;
import ml.darklyn.RUVaiAbrir.time.TimeService;
import ml.darklyn.RUVaiAbrir.user.User;
import ml.darklyn.RUVaiAbrir.user.UserRepository;
import ml.darklyn.RUVaiAbrir.user.roles.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.mock.mockito.SpyBeans;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.*;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest({ StatusController.class, StatusService.class })
@SpyBeans({
        @SpyBean(StatusService.class)
})
public class StatusControllerTests {

    public static final String JWTOKEN = "Bearer Test.Proposes.JWToken";

    @Autowired
    private MockMvc mvc;

    @SpyBean
    private TimeService timeService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private StatusRepository statusRepository;

    @MockBean
    private UserStatusRepository userStatusRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private JacksonTester jacksonTester;

    @Before
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Before
    public void setUpUser() {
        var user = new User();
        user.setId(0L);
        user.setUsername("Moskito");
        user.setEmail("moskito@mockito.mvc");
        user.setPassword("senha.mizeravelmente.insegura");
        user.setRoles(Set.of(new Role(RoleName.ROLE_USER)));

        given(jwtTokenProvider.validateToken(JWTOKEN.substring(7)))
            .willReturn(true);
        given(jwtTokenProvider.getUserIdFromJWT(JWTOKEN.substring(7)))
            .willReturn(0L);

        given(userRepository.findById(0L))
            .willReturn(Optional.of(user));

    }

    @Test
    public void contextLoads() {
    }

    public static Clock utcClockOf(int hour, int minute) {
        ZoneOffset offset = ZoneOffset.UTC;
        Instant inst = LocalTime
                .of(hour, minute)
                .atOffset(offset)
                .atDate(LocalDate.now())
                .toInstant();
        return Clock.fixed(inst, offset.normalized());
    }

    @Test
    public void testStatusClosedDinner() throws Exception {
        given(timeService.getCurrentDateTime())
                .willReturn(ZonedDateTime.now(utcClockOf(13,00)));

        var response = mvc.perform(
                 get("/status")
                .header("Authorization", JWTOKEN)
                .accept(MediaType.APPLICATION_JSON)
            ).andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.OK.value());

        assertThat(response.getContentAsString())
                .isEqualTo(jacksonTester.write(new StatusDTO(RestaurantStatus.CLOSED)));

//        var status =( (Status) statusController.getStatus().getBody()).getRestaurantStatus();
//        System.out.println(status);
    }


}
