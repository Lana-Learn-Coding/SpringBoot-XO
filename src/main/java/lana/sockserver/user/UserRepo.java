package lana.sockserver.user;

import org.springframework.data.repository.PagingAndSortingRepository;


public interface UserRepo extends PagingAndSortingRepository<UserEntity,Integer> {
}
