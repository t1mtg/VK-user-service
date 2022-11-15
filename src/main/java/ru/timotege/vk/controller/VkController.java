package ru.timotege.vk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

@RequestMapping(path = "/vk", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController("controller")
@Qualifier
@Slf4j
public class VkController {

    private final VkService vkService;

    public VkController(VkService vkService) {
        this.vkService = vkService;
    }

    @Operation(summary = "Get name, surname and whether user belongs to the group",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User's name, middle name, surname and group member status",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Provided data is not correct.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", description = "User with provided id not found.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PostMapping("/is-member")
    public ResponseDTO getUser(@RequestHeader("vk_service_token") String accessToken,
                               @Valid @RequestBody() RequestDTO requestDTO
    ) {
        log.info("Getting information about user, id = {}", requestDTO.getUserId());
        return vkService.getUsersData(accessToken, requestDTO);
    }

    @GetMapping("/greet")
    public String getGreet() {
        return "Hello!";
    }

    @ExceptionHandler(VkUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleException(VkUserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
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
