package ru.timotege.vk.dto.vk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VkGetUserResponseDTO {

    VkResponseDTO[] response;
}
