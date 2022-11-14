package ru.timotege.vk.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.timotege.vk.dto.user.UserRequestDTO;
import ru.timotege.vk.dto.user.UserResponseDTO;
import ru.timotege.vk.service.impl.UserDetailsServiceImpl;

import javax.validation.Valid;
import java.util.Objects;

@RequestMapping("/users")
@RestController
@Validated
public class UserController {

    private final UserDetailsServiceImpl userService;

    public UserController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserResponseDTO getUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        return userService.saveUser(requestDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleException(MethodArgumentNotValidException e) {
        String message = Objects.requireNonNull(e.getFieldError()).getDefaultMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(message);
    }
}
