package lana.sockserver.user.controller;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UserForm {
    @Size(max = 20, min = 6)
    @NotBlank
    private String name;

    @Size(max = 20, min = 6)
    @NotBlank
    private String password;

    @Email
    @NotBlank
    private String email;
}