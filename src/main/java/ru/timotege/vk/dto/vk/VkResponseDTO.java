package ru.timotege.vk.dto.vk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VkResponseDTO {

    @JsonProperty("id")
    int id;

    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("last_name")
    String lastName;

    @JsonProperty("nickname")
    String middleName;

    @JsonProperty("can_access_closed")
    boolean canAccessClosed;

    @JsonProperty("is_closed")
    boolean isClosed;
}
