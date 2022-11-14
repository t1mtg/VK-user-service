package ru.timotege.vk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.timotege.vk.dto.vk.RequestDTO;
import ru.timotege.vk.dto.vk.ResponseDTO;
import ru.timotege.vk.exception.VkException;
import ru.timotege.vk.exception.VkUserNotFoundException;
import ru.timotege.vk.service.VkService;

import javax.validation.Valid;
import java.util.Objects;

@RequestMapping("/vk")
@RestController
public class VkController {

    private final VkService vkService;

    public VkController(VkService vkService) {
        this.vkService = vkService;
    }

    @GetMapping("/get-info")
    public ResponseDTO getUser(@RequestHeader("vk_service_token") String access_token,
                               @Valid @RequestBody RequestDTO requestDTO
    ) throws JsonProcessingException {
        return vkService.getUsersData(access_token, requestDTO);
    }

    @ExceptionHandler(VkUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleException(VkUserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(VkException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleException(VkException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
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
