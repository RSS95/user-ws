package in.home.user.api.service;

import in.home.user.api.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {

  @PostMapping(value = "/loadUserByUsername")
  User loadUserByUsername(@RequestBody String userName);

  @PostMapping(value = "/create")
  User create(@RequestBody User user);

  @GetMapping(value = "/create-dummy")
  User createDummy();
}
