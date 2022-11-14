package ru.timotege.vk.dto.vk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {

    @JsonProperty("user_id")
    @NotBlank(message = "User id must not be blank.")
    public String userId;

    @JsonProperty("group_id")
    @NotBlank(message = "Group id must not be blank.")
    public String groupId;
}
