package lana.sockserver.user.role;

import lana.sockserver.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String roleName;

    // The user's side own the relationship.
    // So here is mappedBy
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
