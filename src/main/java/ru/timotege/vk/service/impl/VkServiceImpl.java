package ru.timotege.vk.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.timotege.vk.api.VkApi;
import ru.timotege.vk.dto.vk.RequestDTO;
import ru.timotege.vk.dto.vk.ResponseDTO;
import ru.timotege.vk.dto.vk.VkGetUserResponseDTO;
import ru.timotege.vk.dto.vk.VkIsMemberResponseDTO;
import ru.timotege.vk.exception.VkException;
import ru.timotege.vk.exception.VkUserNotFoundException;
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
    @Cacheable("userCache")
    public ResponseDTO getUsersData(String accessToken, RequestDTO data) {
        var user = getUser(accessToken, data);

        //Here we use id from received user because ID provided in user's query could be String not Integer.
        boolean isMember = isMember(accessToken, user.getResponse()[0].getId(), data.groupId);

        return new ResponseDTO(
                user.getResponse()[0].getLastName(),
                user.getResponse()[0].getFirstName(),
                user.getResponse()[0].getMiddleName(),
                isMember
        );
    }

    private static void catchVkError(ObjectMapper mapper, String vkResponse) {
        try {
            JsonNode jsonErrorResponse = mapper.readTree(vkResponse).get("error");
            if (jsonErrorResponse != null) {
                var message = jsonErrorResponse.get("error_msg");
                throw new VkException(String.valueOf(message));
            }
        } catch (JsonProcessingException e) {
            throw new VkException(e.getMessage());
        }

    }

    private static void validateId(VkGetUserResponseDTO response) {
        if (response.getResponse().length == 0) {
            throw new VkUserNotFoundException();
        }
    }

    private VkGetUserResponseDTO getUser(String accessToken, RequestDTO data) {
        ObjectMapper mapper = new ObjectMapper();

        String vkResponse = vkApi.getUserData(accessToken, data.getUserId(), "nickname", version);

        catchVkError(mapper, vkResponse);

        VkGetUserResponseDTO user;
        try {
            user = mapper.readValue(vkResponse, VkGetUserResponseDTO.class);
        } catch (JsonProcessingException e) {
            throw new VkException(e.getMessage());
        }
        validateId(user);

        return user;
    }

    private boolean isMember(String accessToken, int userId, String groupId) {
        ObjectMapper mapper = new ObjectMapper();

        String vkResponse = vkApi.isMember(accessToken, groupId, userId, version);

        catchVkError(mapper, vkResponse);
        VkIsMemberResponseDTO apiIsMemberResponse;
        try {
            apiIsMemberResponse = mapper.readValue(vkResponse, VkIsMemberResponseDTO.class);
        } catch (JsonProcessingException e) {
            throw new VkException(e.getMessage());
        }

        return apiIsMemberResponse.getResponse() == 1;
    }
}
