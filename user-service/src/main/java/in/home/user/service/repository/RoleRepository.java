package in.home.user.service.repository;

import in.home.user.service.custom.RoleRepositoryCustom;
import in.home.user.service.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>, RoleRepositoryCustom {

  RoleEntity findByRoleName(String roleName);
}
