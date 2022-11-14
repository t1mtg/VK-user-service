package ru.timotege.vk.controller;

import org.springframework.web.bind.annotation.*;
import ru.timotege.vk.dto.user.UserRequestDTO;
import ru.timotege.vk.dto.user.UserResponseDTO;
import ru.timotege.vk.service.impl.UserDetailsServiceImpl;

@RequestMapping("/users")
@RestController
public class UserController {

    private final UserDetailsServiceImpl userService;

    public UserController(UserDetailsServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserResponseDTO getUser(@RequestBody UserRequestDTO requestDTO) {
        return userService.saveUser(requestDTO);
    }
}
