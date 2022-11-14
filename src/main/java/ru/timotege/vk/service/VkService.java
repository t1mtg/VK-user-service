package ru.timotege.vk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.timotege.vk.dto.vk.RequestDTO;
import ru.timotege.vk.dto.vk.ResponseDTO;

public interface VkService {
    ResponseDTO getUsersData(String access_token, RequestDTO data) throws JsonProcessingException;
}
