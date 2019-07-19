package lana.sockserver.user;

import lana.sockserver.user.model.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface UserRepo extends PagingAndSortingRepository<UserEntity,Integer> {
}
