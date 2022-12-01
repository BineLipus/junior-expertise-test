package com.outfit7.rest;

import com.outfit7.entity.User;
import com.outfit7.entity.exception.EntityNotFoundException;
import com.outfit7.json.JsonUtils;
import com.outfit7.services.OpponentsService;
import com.outfit7.services.UserService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.outfit7.utils.TestUtils.users;
import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@QuarkusTest
class MatchingFacadeTest {

    @InjectMock
    OpponentsService opponentsService;

    @Test
    void shouldReturnClassicOpponentsForSelectedUserId() throws JAXBException {
        // Given
        String userId = "some-user-id";
        List<User> opponents = users();

        given(opponentsService.matchOpponents(userId))
                .willReturn(opponents);

        // When
        Response response = given()
                .when()
                .get("/matching/classic/{userId}", userId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(OK.getStatusCode());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON);

        List<User> returnedOpponents = JsonUtils.deserializeToList(response.getBody().asString(), User.class);
        assertThat(returnedOpponents).isEqualTo(opponents);
    }

    @Test
    void shouldReturnUniquelyNamedClassicOpponentsForSelectedUserId() throws JAXBException {
        // For each user check if all opponents are uniquely named. This test case may take a little longer to execute,
        // but if it passes, it is certain that opponent names are unique.
        UserService userService = new UserService();
        for (User user : userService.getAll()) {
            // Given
            String userId = user.getId();

            // When
            Response response = given()
                    .baseUri("http://localhost:8080")
                    .when()
                    .get("/matching/classic/{userId}", userId);

            // Then
            assertThat(response.getStatusCode() == OK.getStatusCode() || response.getStatusCode() == NOT_FOUND.getStatusCode()).isTrue();
            if (response.getStatusCode() == NOT_FOUND.getStatusCode())
                continue; // If no opponents were found, just continue

            assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON);

            List<User> returnedOpponents = JsonUtils.deserializeToList(response.getBody().asString(), User.class);
            assertThat(allOpponentsUniquelyNamed(returnedOpponents)).isTrue();
        }
    }

    @Test
    void shouldReturnRankedOpponentsForSelectedUserId() throws JAXBException {
        // Given
        String userId = "some-user-id";
        List<User> opponents = users();

        given(opponentsService.matchOpponentsRanked(userId))
                .willReturn(opponents);

        // When
        Response response = given()
                .when()
                .get("/matching/ranked/{userId}", userId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(OK.getStatusCode());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON);

        List<User> returnedOpponents = JsonUtils.deserializeToList(response.getBody().asString(), User.class);
        assertThat(returnedOpponents).isEqualTo(opponents);
    }

    @Test
    void shouldReturnRankedOpponentsError404ForSelectedUserId() {
        // Given
        String userId = "some-user-id";

        given(opponentsService.matchOpponentsRanked(userId))
                .willThrow(new EntityNotFoundException("No ranked opponents found for user with id " + userId));

        // When
        Response response = given()
                .when()
                .get("/matching/ranked/{userId}", userId);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND.getStatusCode());
    }

    private boolean allOpponentsUniquelyNamed(List<User> opponents) {
        Set<String> userNames = new HashSet<>();
        for (User u : opponents) {
            userNames.add(u.getPlayerName());
        }
        return (userNames.size() == opponents.size());
    }
}