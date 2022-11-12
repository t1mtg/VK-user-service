package ru.timotege.vk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {

    @JsonProperty("user_id")
    public String userId;

    @JsonProperty("group_id")
    public String groupId;
}
