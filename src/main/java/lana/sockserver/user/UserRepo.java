package lana.sockserver.user;

import lana.sockserver.user.model.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;


public interface UserRepo extends PagingAndSortingRepository<UserEntity, Integer> {
    boolean existsByName(String username);

    Optional<UserEntity> findByName(String username);
}
