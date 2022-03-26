package in.home.user.api.service;

import in.home.user.api.model.User;
import in.home.user.api.query.user.UserQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserService {

  @PostMapping(value = "/user-ws/user/save")
  User save(@RequestBody User user);

  @PostMapping(value = "/user-ws/user/saveAll")
  List<User> saveAll(@RequestBody List<User> userList);

  @PostMapping(value = "/user-ws/user/loadUserByUsernameWithPassword")
  User loadUserByUsernameWithPassword(@RequestBody String userName);

  @PostMapping(value = "/user-ws/user/loadUserByUsernameWithoutPassword")
  User loadUserByUsernameWithoutPassword(@RequestBody String userName);

  @GetMapping(value = "/user-ws/user/findAll")
  List<User> findAll();

  @PostMapping(value = "/user-ws/user/findById")
  User findById(@RequestBody Long userId);

  @PostMapping(value = "/user-ws/user/count")
  Long count(@RequestBody UserQuery userQuery);

  @PostMapping(value = "/user-ws/user/fetch")
  List<User> fetch(@RequestBody UserQuery userQuery);
}
