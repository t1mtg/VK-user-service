package ru.timotege.vk.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank
    @Size(message = "Username should be from 4 to 20 characters", min = 4, max = 20)
    String username;

    @Size(message = "Password should be from 8 to 20  characters", min = 8, max = 20)
    @Pattern(message = """
            Password should contain:\s
            1. At least one numeric character.\s
            2. At least one lowercase character.\s
            3. At least one uppercase character.\s
            4. At least one special symbol among @#$%""",
            regexp = "^(?=.*)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$"
    )
    String password;
}
