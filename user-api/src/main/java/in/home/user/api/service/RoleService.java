package in.home.user.api.service;

import in.home.user.api.model.Role;
import in.home.user.api.query.role.RoleQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = "/role")
public interface RoleService {

    @PostMapping(value = "/save")
    Role save(@RequestBody Role role);

    @PostMapping(value = "/saveAll")
    List<Role> saveAll(@RequestBody List<Role> roleList);

    @PostMapping(value = "/loadUserByRoleName")
    Role loadUserByRoleName(@RequestBody String roleName);

    @GetMapping(value = "/findAll")
    List<Role> findAll();

    @PostMapping(value = "/findById")
    Role findById(@RequestBody Long roleId);

    @PostMapping(value = "/count")
    Long count(@RequestBody RoleQuery roleQuery);

    @PostMapping(value = "/fetch")
    List<Role> fetch(@RequestBody RoleQuery roleQuery);

    @PostMapping(value = "/delete")
    void delete(@RequestBody Long id);

    @PostMapping(value = "/deleteAll")
    void deleteAll(@RequestBody List<Long> idList);

    @PostMapping(value = "/activate")
    boolean activate(@RequestBody Long id);

    @PostMapping(value = "/activateAll")
    boolean activateAll(@RequestBody List<Long> idList);

    @PostMapping(value = "/deactivate")
    boolean deactivate(@RequestBody Long id);

    @PostMapping(value = "/deactivateAll")
    boolean deactivateAll(@RequestBody List<Long> idList);
}
