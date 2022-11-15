package ru.timotege.vk.dto.vk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDTO {

    @JsonProperty("last_name")
    public String lastName;

    @JsonProperty("first_name")
    public String firstName;

    @JsonProperty("middle_name")
    public String middleName;

    @JsonProperty("member")
    public boolean member;
}
