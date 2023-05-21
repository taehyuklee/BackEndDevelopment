package user.login.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import user.login.domain.entity.UserEntity;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    
}
