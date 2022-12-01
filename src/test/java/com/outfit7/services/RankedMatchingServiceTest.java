package com.outfit7.services;

import com.outfit7.entity.User;
import com.outfit7.entity.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.outfit7.utils.TestUtils.user;
import static com.outfit7.utils.TestUtils.users;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RankedMatchingServiceTest {

    @Mock
    UserService userService;

    @InjectMocks
    RankedMatchingService rankedMatchingService;

    @Test
    void shouldRetrieve5OpponentsForUserId() {
        // Given
        String userId = "some-user-id";
        given(userService.get(userId))
                .willReturn(user());
        given(userService.getAll())
                .willReturn(users());

        // When
        List<User> opponents = rankedMatchingService.retrieveOpponents(userId);

        // Then
        assertThat(opponents)
                .hasSize(5)
                .extracting(User::getRank)
                .containsAnyOf(10L, 20L, 30L, 40L, 50L, 60L, 70L, 80L, 90L);
    }

    @Test
    void shouldReturnError() {
        // Given
        String userId = "some-user-id";
        List<User> users = new java.util.ArrayList<>(users()); // Making object mutable (Generic List is not mutable, because it has no implementation)
        users.remove(users.size() - 1); // Remove the last user from users, so retrieveOpponents returns an error.
        given(userService.get(userId))
                .willReturn(user());
        given(userService.getAll())
                .willReturn(users);

        // That
        Exception exception = assertThrows(EntityNotFoundException.class, () -> rankedMatchingService.retrieveOpponents(userId));
        assertThat(exception.getMessage().equals("No ranked opponents found for user with id " + user().getId())).isTrue();
    }
}