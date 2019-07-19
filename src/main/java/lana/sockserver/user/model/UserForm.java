package lana.sockserver.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserForm {
    private Integer id;
    private String name;
    private String phone;
}
