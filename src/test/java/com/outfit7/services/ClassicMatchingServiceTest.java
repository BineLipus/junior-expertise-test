package com.outfit7.services;

import com.outfit7.entity.User;
import com.outfit7.entity.exception.EntityNotFoundException;
import com.outfit7.utils.TestUtils;
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
class ClassicMatchingServiceTest {

    @Mock
    UserService userService;

    @InjectMocks
    ClassicMatchingService classicMatchingService;

    @Test
    void shouldRetrieveOpponentsForUserId() {
        // Given
        String userId = "some-user-id";
        given(userService.get(userId))
                .willReturn(user());
        given(userService.getAll())
                .willReturn(users());

        // When
        List<User> opponents = classicMatchingService.retrieveOpponents(userId);

        // Then
        assertThat(opponents)
                .hasSize(4)
                .extracting(User::getId)
                .containsExactly("2", "3", "5", "7");
        // Removed id "6" as User with playerName="name4" was already added with id "5", added id "7" as a result
        // of changes to filter sequence in implementations of MatchingService.
    }

    @Test
    void shouldReturnError() {
        // Given
        String userId = "some-user-id";
        List<User> users = List.of(User.builder().playerName("name5").powerLevel(30L).rank(250L).id("7").hero(TestUtils.hero(4L)).champions(TestUtils.champions()).build());
        // User "name5" has too high powerLevel to match with user(), so this should cause no opponents to be returned.
        given(userService.get(userId))
                .willReturn(user());
        given(userService.getAll())
                .willReturn(users);

        // That
        Exception exception = assertThrows(EntityNotFoundException.class, () -> classicMatchingService.retrieveOpponents(userId));
        assertThat(exception.getMessage().equals("No classic opponents found for user with id " + user().getId())).isTrue();
    }
}