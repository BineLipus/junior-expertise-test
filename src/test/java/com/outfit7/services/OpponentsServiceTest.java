package com.outfit7.services;

import static com.outfit7.utils.TestUtils.users;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.outfit7.entity.User;

@ExtendWith(MockitoExtension.class)
class OpponentsServiceTest {

    @Mock
    ClassicMatchingService classicMatchingService;

    @InjectMocks
    OpponentsService opponentsService;

    @Test
    void shouldRetrieveOpponentsForUserId() {
        // Given
        String userId = "some-user-id";
        given(classicMatchingService.retrieveOpponents(userId))
                .willReturn(users());

        // When
        List<User> opponents = opponentsService.matchOpponents(userId);

        // Then
        assertThat(opponents)
                .hasSize(10);
    }

}