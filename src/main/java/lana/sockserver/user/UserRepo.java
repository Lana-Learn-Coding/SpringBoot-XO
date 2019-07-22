package lana.sockserver.user;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;


public interface UserRepo extends PagingAndSortingRepository<User, Integer> {
    boolean existsByName(String username);

    Optional<User> findByName(String username);
}
