package com.tfg.GoAway.user.user.application.user.register;

import com.tfg.GoAway.user.user.domain.User;
import com.tfg.GoAway.user.user.domain.UserCriteria;
import com.tfg.GoAway.user.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRegisterTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRegisterMapper userRegisterMapper;

    @InjectMocks
    private UserRegister userRegister;

    private UserRegisterRecord validRecord;

    @BeforeEach
    void setUp() {
        validRecord = UserRegisterRecord.builder()
                .email("test@example.com")
                .name("Test User")
                .password("password123")
                .contactNumber(1234567890L)
                .build();
    }

    @Test
    void testRegisterSuccess() {
        when(userRepository.finder(any(UserCriteria.class))).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userRegister.register(validRecord));

        verify(userRepository).finder(any(UserCriteria.class));
        verify(userRegisterMapper, never()).toDomain(any(), any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testRegisterUserAlreadyExists() throws Exception {
        User mockUser = mock(User.class);
        when(userRepository.finder(any(UserCriteria.class))).thenReturn(Optional.of(mockUser));

        Exception exception = assertThrows(Exception.class, () -> {
            userRegister.register(validRecord);
        });
        assertEquals("El usuario ya existe", exception.getMessage());
        verify(userRepository).finder(any(UserCriteria.class));
        verify(userRegisterMapper, never()).toDomain(any(), any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testRegisterNullEmail() throws Exception {
        UserRegisterRecord invalidRecord = UserRegisterRecord.builder()
                .email(null)
                .name("Test User")
                .password("password123")
                .contactNumber(1234567890L)
                .build();

        Exception exception = assertThrows(Exception.class, () -> {
            userRegister.register(invalidRecord);
        });
        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(userRepository, never()).finder(any(UserCriteria.class));
        verify(userRegisterMapper, never()).toDomain(any(), any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testRegisterSaveError() {
        when(userRepository.finder(any(UserCriteria.class))).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userRegister.register(validRecord));

        verify(userRepository).finder(any(UserCriteria.class));
        verify(userRegisterMapper, never()).toDomain(any(), any());
        verify(userRepository, never()).save(any());
    }
}