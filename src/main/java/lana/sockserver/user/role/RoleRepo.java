package lana.sockserver.user.role;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepo extends CrudRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByRoleName(String roleName);
}
