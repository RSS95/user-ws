package in.home.user.api.service;

import in.home.user.api.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "/user")
public interface UserService {

  @PostMapping(value = "/save")
  User save(@RequestBody User user);

  @PostMapping(value = "/saveAll")
  List<User> saveAll(@RequestBody List<User> userList);

  @PostMapping(value = "/loadUserByUsername")
  User loadUserByUsername(@RequestBody String userName);

  @GetMapping(value = "/findAll")
  List<User> findAll();

  @PostMapping(value = "/findById")
  User findById(@RequestBody Long userId);
}
