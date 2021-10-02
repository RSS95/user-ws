package in.home.user.service.impl;

import in.home.user.api.model.User;
import in.home.user.api.service.UserService;
import in.home.user.service.entity.RoleEntity;
import in.home.user.service.entity.UserEntity;
import in.home.user.service.repository.UserRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/user")
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;

  @Override
  public User create(User user) {
    UserEntity userEntity = UserEntity.builder().build();
    userEntity.copyToEntity(user);
    userRepository.save(userEntity);
    return user;
  }

  @Override
  public User createDummy() {
    RoleEntity roleEntity = RoleEntity.builder().roleName("admin").active(true).build();
    UserEntity userEntity =
        UserEntity.builder()
            .userName("admin")
            .password("password")
            .email("admin@application.in")
            .roleList(Collections.singletonList(roleEntity))
            .active(true)
            .createdBy(1L)
            .build();
    userRepository.save(userEntity);
    User user = User.builder().build();
    userEntity.copyToDTO(user);
    return user;
  }

  @Override
  public User loadUserByUsername(String username) {
    UserEntity userEntity = userRepository.findByUserName(username);
    User user = User.builder().build();
    userEntity.copyToDTO(user);
    return user;
  }

  @Override
  public List<User> findAll() {
    List<UserEntity> userEntityList = userRepository.findAll();
    return userEntityList.stream()
        .map(
            userEntity -> {
              User user = User.builder().build();
              userEntity.copyToDTO(user);
              return user;
            })
        .collect(Collectors.toList());
  }

  @Override
  public User findById(Long userId) {
    Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
    if (userEntityOptional.isPresent()) {
      User user = User.builder().build();
      userEntityOptional.get().copyToDTO(user);
      return user;
    }
    return null;
  }
}
