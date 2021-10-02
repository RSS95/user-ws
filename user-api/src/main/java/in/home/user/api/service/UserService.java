package in.home.user.api.service;

import in.home.user.api.model.User;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {

  @PostMapping(value = "/create")
  User create(@RequestBody User user);

  @GetMapping(value = "/create-dummy")
  User createDummy();

  @PostMapping(value = "/loadUserByUsername")
  User loadUserByUsername(@RequestBody String userName);

  @GetMapping(value = "/findAll")
  List<User> findAll();

  @PostMapping(value = "/findById")
  User findById(@RequestBody Long userId);
}
