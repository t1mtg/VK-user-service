package ru.timotege.vk.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.timotege.vk.api.VkApi;
import ru.timotege.vk.dto.RequestDTO;
import ru.timotege.vk.dto.ResponseDTO;
import ru.timotege.vk.dto.VkGetUserResponseDTO;
import ru.timotege.vk.dto.VkIsMemberResponseDTO;
import ru.timotege.vk.service.VkService;

@Service
public class VkServiceImpl implements VkService {

    private final VkApi vkApi;

    @Value("${Vk.version}")
    private String version;

    public VkServiceImpl(VkApi vkApi) {
        this.vkApi = vkApi;
    }

    @Override
    public ResponseDTO getUsersData(String access_token, RequestDTO data) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        VkGetUserResponseDTO vkGetUserResponseDTO = mapper.readValue(
                vkApi.getUserData(access_token, data.getUserId(), "nickname", version),
                VkGetUserResponseDTO.class
        );

        VkIsMemberResponseDTO apiIsMemberResponse = mapper.readValue(
                vkApi.isMember(
                        access_token,
                        data.groupId,
                        vkGetUserResponseDTO.getResponse()[0].getId(),
                        version
                ), VkIsMemberResponseDTO.class
        );

        boolean isMember = apiIsMemberResponse.getResponse() == 1;

        return new ResponseDTO(
                vkGetUserResponseDTO.getResponse()[0].getLastName(),
                vkGetUserResponseDTO.getResponse()[0].getFirstName(),
                vkGetUserResponseDTO.getResponse()[0].getMiddleName(),
                isMember
        );

    }
}
