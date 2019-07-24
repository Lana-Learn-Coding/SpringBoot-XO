package lana.sockserver.user.role;

import lana.sockserver.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
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
