package in.home.user.service.repository;

import in.home.user.service.custom.UserRepositoryCustom;
import in.home.user.service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, UserRepositoryCustom {

  UserEntity findByUserName(String userName);
}
