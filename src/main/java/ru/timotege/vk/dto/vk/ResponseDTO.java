package ru.timotege.vk.dto.vk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    public String lastName;

    public String firstName;

    public String middleName;

    public boolean member;
}
