package com.outfit7.services;

import com.outfit7.entity.User;
import com.outfit7.entity.User.Champion;
import com.outfit7.entity.User.Hero;
import com.outfit7.entity.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Test
    void shouldReturnAllUsers() {
        // Given, when, then
        assertThat(userService.getAll())
                .hasSize(100);
    }

    @Test
    void shouldReturnOneUser() {
        // Given
        String userId = "f4afb2fd-b4b9-41fe-ab7c-55059870e78a";
        User expectedUser = User.builder()
                .id(userId)
                .playerName("Vedast Swiftfoot")
                .powerLevel(2L)
                .rank(14L) // Added additional property as it was now present in User before.
                .hero(Hero.builder()
                        .id("ea243b0a-f225-4835-907e-887d5f202a23")
                        .level(44L)
                        .name("Sephven")
                        .build())
                .champions(List.of(
                        Champion.builder()
                                .id("d8cf1f81-2835-4e23-9ca9-3e6dfe532f0c")
                                .level(1L)
                                .name("Hal")
                                .build(),
                        Champion.builder()
                                .id("8ffecd14-a2ec-4150-bc7b-dbb6271910b6")
                                .level(2L)
                                .name("Terwise")
                                .build(),
                        Champion.builder()
                                .id("477c3f01-fde4-4c80-9ff0-98229af45cb2")
                                .level(3L)
                                .name("Leofty")
                                .build()))
                .build();

        // When, then
        assertThat(userService.get(userId))
                .isEqualTo(expectedUser);
    }

    @Test
    void shouldThrowExceptionIfUserDoesNotExist() {
        // Given, when
        Throwable thrown = catchThrowable(() -> userService.get("some-id-that-does-not-exist"));

        // Then
        assertThat(thrown)
                .isInstanceOf(EntityNotFoundException.class);
    }

}