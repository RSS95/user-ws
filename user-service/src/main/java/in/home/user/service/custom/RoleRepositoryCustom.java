package in.home.user.service.custom;

import in.home.user.api.query.role.RoleQuery;
import in.home.user.service.entity.RoleEntity;

import java.util.List;

public interface RoleRepositoryCustom {

  Long count(RoleQuery roleQuery);

  List<RoleEntity> fetch(RoleQuery roleQuery);
}
