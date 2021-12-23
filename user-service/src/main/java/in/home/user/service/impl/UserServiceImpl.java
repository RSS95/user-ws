package in.home.user.service.impl;

import in.home.user.api.model.User;
import in.home.user.api.query.user.UserQuery;
import in.home.user.api.service.UserService;
import in.home.user.service.entity.UserEntity;
import in.home.user.service.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;

  @Override
  public User save(User user) {
    log.trace("input parameter::user:{}", user.toString());
    UserEntity userEntity = new UserEntity();
    userEntity.copyToEntity(user);
    userEntity.setPassword(user.getPassword());
    log.trace("converted UserEntity from User DTO::userEntity:{}", userEntity.toString());
    UserEntity userEntitySaved = userRepository.save(userEntity);
    log.debug("saved UserEntity:{}", userEntitySaved.toString());
    if (user.getId() == null) {
      log.info(
          "user inserted:::id:{}::username:{}",
          userEntitySaved.getId(),
          userEntitySaved.getUserName());
    } else {
      log.info(
          "user updated:::id:{}::username:{}",
          userEntitySaved.getId(),
          userEntitySaved.getUserName());
    }
    return convertToDTO(userEntitySaved);
  }

  @Override
  public List<User> saveAll(List<User> userList) {
    List<UserEntity> userEntityList = convertToEntity(userList);
    userRepository.saveAll(userEntityList);
    return convertToDTO(userEntityList);
  }

  @Override
  public User loadUserByUsernameWithPassword(String username) {
    UserEntity userEntity = userRepository.findByUserName(username);
    User user = new User();
    userEntity.copyToDTO(user);
    user.setPassword(userEntity.getPassword());
    return user;
  }

  @Override
  public User loadUserByUsernameWithoutPassword(String username) {
    UserEntity userEntity = userRepository.findByUserName(username);
    User user = new User();
    userEntity.copyToDTO(user);
    return user;
  }

  @Override
  public List<User> findAll() {
    List<UserEntity> userEntityList = userRepository.findAll();
    return convertToDTO(userEntityList);
  }

  @Override
  public User findById(Long userId) {
    Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
    return userEntityOptional.map(this::convertToDTO).orElse(null);
  }

  @Override
  public Long count(UserQuery userQuery) {
    return userRepository.count(userQuery);
  }

  @Override
  public List<User> fetch(UserQuery userQuery) {
    List<UserEntity> userEntityList = userRepository.fetch(userQuery);
    return convertToDTO(userEntityList);
  }

  private List<User> convertToDTO(List<UserEntity> userEntityList) {
    List<User> userList = new ArrayList<>();
    for (UserEntity userEntity : userEntityList) {
      User user = new User();
      userEntity.copyToDTO(user);
      userList.add(user);
    }
    return userList;
  }

  private User convertToDTO(UserEntity userEntity) {
    User user = new User();
    userEntity.copyToDTO(user);
    return user;
  }

  private List<UserEntity> convertToEntity(List<User> userList) {
    List<UserEntity> userEntityList = new ArrayList<>();
    for (User user : userList) {
      UserEntity userEntity = new UserEntity();
      userEntity.copyToEntity(user);
      userEntityList.add(userEntity);
    }
    return userEntityList;
  }

  private UserEntity convertToEntity(User user) {
    UserEntity userEntity = new UserEntity();
    userEntity.copyToEntity(user);
    return userEntity;
  }
}
