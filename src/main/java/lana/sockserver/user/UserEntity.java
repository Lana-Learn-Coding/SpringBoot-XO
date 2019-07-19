package lana.sockserver.user;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Size(min = 6, max = 20)
    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @Size(min = 6,max = 20)
    @NotBlank
    private String password;

    @NotBlank
    private String salt;
}
