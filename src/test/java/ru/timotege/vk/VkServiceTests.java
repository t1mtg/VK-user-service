package ru.timotege.vk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Value;
import ru.timotege.vk.api.VkApi;
import ru.timotege.vk.dto.vk.RequestDTO;
import ru.timotege.vk.dto.vk.ResponseDTO;
import ru.timotege.vk.exception.VkException;
import ru.timotege.vk.service.impl.VkServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VkServiceTests {
    VkApi vkApi;

    @InjectMocks
    private VkServiceImpl vkService;

    @BeforeEach
    public void init() {
        vkApi = mock(VkApi.class);
        vkService = new VkServiceImpl(vkApi);
    }

    @Value("${Vk.version}")
    private String version;

    @Test
    public void getUsersData_SuccessPath_expectingDataReturned() {
        //ARRANGE
        String accessToken = "accessToken";
        String userId = "317837708";
        String groupId = "jumoreski";

        RequestDTO requestDTO = new RequestDTO(userId, groupId);
        ResponseDTO expectedResponseDTO = new ResponseDTO("Gurman", "Timofey", "", true);

        String jsonGetUserResponse = "{\"response\":[{\"id\":317837708,\"nickname\":\"\",\"first_name\":\"Timofey\",\"last_name\":\"Gurman\",\"can_access_closed\":true,\"is_closed\":false}]}";
        when(vkApi.getUserData(accessToken, userId, "nickname", version)).thenReturn(jsonGetUserResponse);

        String isMemberResponse = "{\"response\":1}";
        when(vkApi.isMember(accessToken, groupId, Integer.parseInt(userId), version)).thenReturn(isMemberResponse);
        //ACT
        ResponseDTO response = vkService.getUsersData(accessToken, requestDTO);

        //ASSERT
        assertEquals(response, expectedResponseDTO);
    }

    @Test
    public void catchVkError_throwException() {
        //ARRANGE
        String accessToken = "accessToken";

        String userId = "317837708";
        String groupId = "jumoreskiiii";
        RequestDTO requestDTO = new RequestDTO(userId, groupId);

        String expectedMessage = "\"One of the parameters specified was missing or invalid: group_id is invalid\"";

        String jsonGetUserResponse = "{\"error\":{\"error_code\":100," +
                "\"error_msg\":\"One of the parameters specified was missing or invalid: group_id is invalid\"," +
                "\"request_params\":[{\"key\":\"v\",\"value\":\"5.131\"},{\"key\":\"group_id\",\"value\":\"jumoreskiee\"}," +
                "{\"key\":\"user_id\",\"value\":\"317837708\"}," +
                "{\"key\":\"method\",\"value\":\"groups.isMember\"}," +
                "{\"key\":\"oauth\",\"value\":\"1\"}]}}";
        when(vkApi.getUserData(accessToken, userId, "nickname", version)).thenReturn(jsonGetUserResponse);

        //ASSERT
        Exception e = assertThrows(VkException.class,() -> vkService.getUsersData(accessToken, requestDTO));
        assertEquals(e.getMessage(), expectedMessage);
    }


}
