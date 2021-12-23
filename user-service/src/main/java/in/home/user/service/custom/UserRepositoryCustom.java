package in.home.user.service.custom;

import in.home.user.api.query.user.UserQuery;
import in.home.user.service.entity.UserEntity;

import java.util.List;

public interface UserRepositoryCustom {

  Long count(UserQuery userQuery);

  List<UserEntity> fetch(UserQuery userQuery);
}
