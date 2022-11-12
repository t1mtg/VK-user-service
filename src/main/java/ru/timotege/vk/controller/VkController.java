package ru.timotege.vk.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.timotege.vk.dto.RequestDTO;
import ru.timotege.vk.dto.ResponseDTO;
import ru.timotege.vk.service.VkService;

@RequestMapping("/users")
@RestController
public class VkController {

    private final VkService vkService;

    @Autowired
    public VkController(VkService vkService) {
        this.vkService = vkService;
    }

    @GetMapping("/get/{access_token}")
    public ResponseDTO getUser(@PathVariable String access_token, @RequestBody RequestDTO requestDTO) throws JsonProcessingException {
        return vkService.getUsersData(access_token, requestDTO);
    }
}
