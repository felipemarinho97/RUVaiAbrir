package ml.darklyn.RUVaiAbrir;

import ml.darklyn.RUVaiAbrir.enumeration.RestaurantStatus;
import ml.darklyn.RUVaiAbrir.exceptions.NotFoundException;
import ml.darklyn.RUVaiAbrir.status.*;
import ml.darklyn.RUVaiAbrir.time.TimeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZonedDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatusServiceTests {

    private StatusService statusService;

    private TimeService timeService;

    private StatusRepository statusRepository;

    private UserStatusRepository userStatusRepository;

    @Before
    public void setUp() {
        timeService = Mockito.spy(TimeService.class);
        statusRepository = Mockito.mock(StatusRepository.class);
        userStatusRepository = Mockito.mock(UserStatusRepository.class);
        statusService = new StatusService(timeService, statusRepository, userStatusRepository);
    }

    @Test
    public void contextLoads() {
    }

    @Test(expected = NotFoundException.class)
    public void currentStatusNonRegisteredTest() {
        when(timeService.getCurrentDateTime())
                .thenReturn(ZonedDateTime.now(StatusControllerTests.utcClockOf(13,00)));
        when(statusRepository.findOneByDateAndMealType(timeService.getCurrentDate(), timeService.getCurrentMealType()))
                .thenReturn(Optional.empty());

        var restaurantStatus = statusService.getCurrentStatus();
    }

    @Test(expected = NotFoundException.class)
    public void currentStatusWrongTimeTest() {
        when(timeService.getCurrentDateTime())
                .thenReturn(ZonedDateTime.now(StatusControllerTests.utcClockOf(13,00)));

        // Create a status resource
        var registeredStatus = new Status();

        // Set CLOSED
        registeredStatus.setRestaurantStatus(RestaurantStatus.CLOSED);

        when(statusRepository.findOneByDateAndMealType(timeService.getCurrentDate(), timeService.getCurrentMealType()))
                .thenReturn(Optional.of(registeredStatus));

        // Set time to night
        when(timeService.getCurrentDateTime())
                .thenReturn(ZonedDateTime.now(StatusControllerTests.utcClockOf(23,00)));

        var restaurantStatus = statusService.getCurrentStatus();
    }

    @Test
    public void currentStatusTest() {
        when(timeService.getCurrentDateTime())
                .thenReturn(ZonedDateTime.now(StatusControllerTests.utcClockOf(13,00)));

        // Create a status resource
        var registeredStatus = new Status();

        // Set CLOSED
        registeredStatus.setRestaurantStatus(RestaurantStatus.CLOSED);

        when(statusRepository.findOneByDateAndMealType(timeService.getCurrentDate(), timeService.getCurrentMealType()))
                .thenReturn(Optional.of(registeredStatus));

        var closedRestaurantStatus = statusService.getCurrentStatus();

        assertThat(closedRestaurantStatus)
            .isEqualTo(registeredStatus);

        // Change to OPENED
        registeredStatus.setRestaurantStatus(RestaurantStatus.CLOSED);

        when(statusRepository.findOneByDateAndMealType(timeService.getCurrentDate(), timeService.getCurrentMealType()))
                .thenReturn(Optional.of(registeredStatus));

        var openedRestaurantStatus = statusService.getCurrentStatus();

        assertThat(openedRestaurantStatus)
                .isEqualTo(registeredStatus);
    }

    @Test(expected = NotFoundException.class)
    public void currentNonRegisteredUserStatusTest() {
        when(timeService.getCurrentDateTime())
                .thenReturn(ZonedDateTime.now(StatusControllerTests.utcClockOf(13,00)));
        when(userStatusRepository.findByDateAndMealType(timeService.getCurrentDate(), timeService.getCurrentMealType()))
                .thenReturn(Optional.empty());

        var restaurantStatus = statusService.getCurrentStatus();
    }


    @Test
    public void currentUserStatusTest() {
        when(timeService.getCurrentDateTime())
                .thenReturn(ZonedDateTime.now(StatusControllerTests.utcClockOf(13,00)));

        var closedStatus = new UserStatus();
        closedStatus.setDate(timeService.getCurrentDate());
        closedStatus.setMealType(timeService.getCurrentMealType());
        closedStatus.setRestaurantStatus(RestaurantStatus.CLOSED);

        var openedStatus = new UserStatus();
        openedStatus.setDate(timeService.getCurrentDate());
        openedStatus.setMealType(timeService.getCurrentMealType());
        openedStatus.setRestaurantStatus(RestaurantStatus.OPENED);

        // 3 Closed and 2 Opened
        List<UserStatus> userStatuses = populationOf5UserStatus();
        when(userStatusRepository.findByDateAndMealType(timeService.getCurrentDate(), timeService.getCurrentMealType()))
                .thenReturn(Optional.of(userStatuses));

        var restaurantStatus = statusService.getCurrentUserStatus();

        assertThat(restaurantStatus)
                .isEqualTo(closedStatus);

        // 3 Closed and 3 Opened
        userStatuses.add(openedStatus);

        when(userStatusRepository.findByDateAndMealType(timeService.getCurrentDate(), timeService.getCurrentMealType()))
                .thenReturn(Optional.of(userStatuses));

        restaurantStatus = statusService.getCurrentUserStatus();

        assertThat(restaurantStatus)
                .isEqualTo(closedStatus);

        // 3 Closed and 4 Opened
        userStatuses.add(openedStatus);

        when(userStatusRepository.findByDateAndMealType(timeService.getCurrentDate(), timeService.getCurrentMealType()))
                .thenReturn(Optional.of(userStatuses));

        restaurantStatus = statusService.getCurrentUserStatus();

        assertThat(restaurantStatus)
                .isEqualTo(openedStatus);

    }

    private List<UserStatus> populationOf5UserStatus() {
        List list = new LinkedList();

        for (int i = 0; i < 5; i++) {
            var userStatus = new UserStatus();
            userStatus.setDate(timeService.getCurrentDate());
            userStatus.setMealType(timeService.getCurrentMealType());
            if (i < 3)
                userStatus.setRestaurantStatus(RestaurantStatus.CLOSED);
            else
                userStatus.setRestaurantStatus(RestaurantStatus.OPENED);

            list.add(userStatus);
        }

        return list;

    }


}
