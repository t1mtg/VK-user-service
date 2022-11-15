package ru.timotege.vk.service;

import ru.timotege.vk.dto.vk.RequestDTO;
import ru.timotege.vk.dto.vk.ResponseDTO;

public interface VkService {
    ResponseDTO getUsersData(String accessToken, RequestDTO data);
}
