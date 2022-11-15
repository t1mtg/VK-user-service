package ru.timotege.vk.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserResponseDTO {

    public final Long id;

    public final String username;
}
