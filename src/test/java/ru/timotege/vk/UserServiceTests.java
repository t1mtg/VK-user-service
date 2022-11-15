package ru.timotege.vk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.timotege.vk.dto.user.UserRequestDTO;
import ru.timotege.vk.dto.user.UserResponseDTO;
import ru.timotege.vk.entity.User;
import ru.timotege.vk.repository.UserRepository;
import ru.timotege.vk.service.impl.UserDetailsServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTests {

    UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserDetailsServiceImpl userService;

    @BeforeEach
    public void init() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(BCryptPasswordEncoder.class);
        userService = new UserDetailsServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    public void loadUserByUsername_successPath_correspondingUserReturned() {
        //ARRANGE
        String username = "username";
        String password = "password";

        User user = new User(username, password);

        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.of(user));

        //ACT
        var foundUser = userService.loadUserByUsername(username);

        //ASSERT
        assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    public void loadUserByUsername_userNotFound_ThrowsException() {
        //ARRANGE
        String username = "username";

        String expectedMessage = "User does not exist";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        //ASSERT
        Exception e = assertThrows(UsernameNotFoundException.class,() -> userService.loadUserByUsername(username));
        assertEquals(e.getMessage(), expectedMessage);
    }

    @Test
    public void saveUser_successPath_saveUserWithSameUsername() {
        //ARRANGE
        String username = "username";
        String password = "password";
        String encodedPassword = "enc0d3dPassw0rd";
        Long id = 1L;

        UserRequestDTO requestDTO = new UserRequestDTO(username, password);
        UserResponseDTO responseDTO = new UserResponseDTO(id, username);
        User user = new User(username, encodedPassword);

        when(passwordEncoder.encode(requestDTO.getPassword())).thenReturn(encodedPassword);
        when(userRepository.save(eq(user))).thenReturn(user);

        //ACT
        var savedUser = userService.saveUser(requestDTO);

        //ASSERT
        assertEquals(savedUser.getUsername(), responseDTO.getUsername());
    }
}
