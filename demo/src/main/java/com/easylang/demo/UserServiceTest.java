// File: UserServiceTest.java

package com.easylang.demo;

import com.easylang.demo.User;
import com.easylang.demo.Role;
import com.easylang.demo.UserRepository;
import com.easylang.demo.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUsername("john.doe");
        user.setPassword("password123");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setRole(Role.TRANSLATOR);
    }

    @Test
    public void testRegisterUser_Success() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(java.util.Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(java.util.Optional.of(user));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(user);
        });

        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testLoginUser_Success() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        User loggedInUser = userService.loginUser(user.getUsername(), user.getPassword());

        assertNotNull(loggedInUser);
        assertEquals(user.getUsername(), loggedInUser.getUsername());
    }

    @Test
    public void testLoginUser_InvalidCredentials() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.loginUser(user.getUsername(), "wrongPassword");
        });

        assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    public void testLoginUser_UserNotFound() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(java.util.Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.loginUser(user.getUsername(), user.getPassword());
        });

        assertEquals("User not found", exception.getMessage());
    }
}