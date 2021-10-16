package in.home.user.service.impl;

import in.home.user.api.model.User;
import in.home.user.api.service.UserService;
import in.home.user.service.entity.UserEntity;
import in.home.user.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;

  @Override
  public User save(User user) {
    UserEntity userEntity = new UserEntity();
    userEntity.copyToEntity(user);
    userRepository.save(userEntity);
    userEntity.copyToDTO(user);
    return user;
  }

  @Override
  public List<User> saveAll(List<User> userList) {
    List<UserEntity> userEntityList = new ArrayList<>();
    for (User user : userList) {
      UserEntity userEntity = new UserEntity();
      userEntity.copyToEntity(user);
      userEntityList.add(userEntity);
    }
    userRepository.saveAll(userEntityList);
    userList.clear();
    for (UserEntity userEntity : userEntityList) {
      User user = new User();
      userEntity.copyToDTO(user);
      userList.add(user);
    }
    return userList;
  }

  @Override
  public User loadUserByUsername(String username) {
    UserEntity userEntity = userRepository.findByUserName(username);
    User user = new User();
    userEntity.copyToDTO(user);
    return user;
  }

  @Override
  public List<User> findAll() {
    List<UserEntity> userEntityList = userRepository.findAll();
    List<User> userList = new ArrayList<>();
    for (UserEntity userEntity : userEntityList) {
      User user = new User();
      userEntity.copyToDTO(user);
      userList.add(user);
    }
    return userList;
  }

  @Override
  public User findById(Long userId) {
    Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
    if (userEntityOptional.isPresent()) {
      User user = new User();
      userEntityOptional.get().copyToDTO(user);
      return user;
    }
    return null;
  }
}
