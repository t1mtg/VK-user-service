package ru.timotege.vk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.timotege.vk.dto.vk.RequestDTO;
import ru.timotege.vk.dto.vk.ResponseDTO;
import ru.timotege.vk.exception.VkException;
import ru.timotege.vk.exception.VkUserNotFoundException;
import ru.timotege.vk.service.VkService;

@RequestMapping("/vk")
@RestController
public class VkController {

    private final VkService vkService;

    public VkController(VkService vkService) {
        this.vkService = vkService;
    }

    @GetMapping("/get/{access_token}")
    public ResponseDTO getUser(@PathVariable String access_token, @RequestBody RequestDTO requestDTO) throws JsonProcessingException {
        return vkService.getUsersData(access_token, requestDTO);
    }


    @ExceptionHandler(VkUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleException(VkUserNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(VkException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(VkException e) {
        return e.getMessage();
    }
}
