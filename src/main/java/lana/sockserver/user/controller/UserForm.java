package lana.sockserver.user.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
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