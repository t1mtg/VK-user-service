package ru.timotege.vk.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.timotege.vk.api.VkApi;
import ru.timotege.vk.dto.vk.*;
import ru.timotege.vk.exception.VkException;
import ru.timotege.vk.exception.VkUserNotFoundException;
import ru.timotege.vk.service.VkService;

@Service
public class VkServiceImpl implements VkService {

    public static final String ERROR = "error";
    public static final String ERROR_MSG = "error_msg";
    public static final String NICKNAME = "nickname";
    private final VkApi vkApi;

    @Value("${Vk.version}")
    private String version;

    public VkServiceImpl(VkApi vkApi) {
        this.vkApi = vkApi;
    }

    @Override
    @Cacheable("userCache")
    public ResponseDTO getUsersData(String accessToken, RequestDTO data) {
        VkResponseDTO user = getUser(accessToken, data);

        //Here we use id from received user because ID provided in user's query could be String not Integer.
        boolean isMember = isMember(accessToken, user.getId(), data.groupId);

        return new ResponseDTO(
                user.getLastName(),
                user.getFirstName(),
                user.getMiddleName(),
                isMember
        );
    }

    private static void catchVkError(ObjectMapper mapper, String vkResponse) {
        try {
            JsonNode jsonErrorResponse = mapper.readTree(vkResponse).get(ERROR);
            if (jsonErrorResponse != null) {
                var message = jsonErrorResponse.get(ERROR_MSG);
                throw new VkException(String.valueOf(message));
            }

        } catch (JsonProcessingException e) {
            throw new VkException(e.getMessage());
        }

    }

    private VkResponseDTO getUser(String accessToken, RequestDTO data) {
        ObjectMapper mapper = new ObjectMapper();

        String vkResponse = vkApi.getUserData(accessToken, data.getUserId(), NICKNAME, version);

        catchVkError(mapper, vkResponse);

        VkGetUserResponseDTO user;
        try {
            user = mapper.readValue(vkResponse, VkGetUserResponseDTO.class);
        } catch (JsonProcessingException e) {
            throw new VkException(e.getMessage());
        }

        if (user.getResponse().length == 0) {
            throw new VkUserNotFoundException();
        }

        return user.getResponse()[0];
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
