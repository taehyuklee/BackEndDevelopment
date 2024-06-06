package maria.practice.usersvc.domain.repository;

import maria.practice.usersvc.domain.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {

}
