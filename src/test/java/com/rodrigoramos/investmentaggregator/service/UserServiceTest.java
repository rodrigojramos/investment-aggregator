package com.rodrigoramos.investmentaggregator.service;

import com.rodrigoramos.investmentaggregator.UserRepository;
import com.rodrigoramos.investmentaggregator.controller.CreateUserDto;
import com.rodrigoramos.investmentaggregator.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Nested
    class createUser {

        @Test
        @DisplayName("Should create a user with success")
        void shouldCreateAUserWithSuccess() {
            // Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "123",
                    Instant.now(),
                    null
            );

            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            var input = new CreateUserDto(
                    "username",
                    "email@email.com",
                    "123"
            );

            // Act
            var output = userService.createUser(input);

            // Assert
            assertNotNull(output);

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOccurs() {
            // Arrange
            doThrow(new RuntimeException()).when(userRepository).save(any());

            var input = new CreateUserDto(
                    "username",
                    "email@email.com",
                    "123"
            );

            // Act & Assert
            assertThrows(RuntimeException.class, () -> userService.createUser(input));
        }
    }

}