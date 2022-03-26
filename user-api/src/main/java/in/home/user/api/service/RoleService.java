package in.home.user.api.service;

import in.home.user.api.model.Role;
import in.home.user.api.query.role.RoleQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface RoleService {

    @PostMapping(value = "/user-ws/role/save")
    Role save(@RequestBody Role role);

    @PostMapping(value = "/user-ws/role/saveAll")
    List<Role> saveAll(@RequestBody List<Role> roleList);

    @PostMapping(value = "/user-ws/role/loadUserByRoleName")
    Role loadUserByRoleName(@RequestBody String roleName);

    @GetMapping(value = "/user-ws/role/findAll")
    List<Role> findAll();

    @PostMapping(value = "/user-ws/role/findById")
    Role findById(@RequestBody Long roleId);

    @PostMapping(value = "/user-ws/role/count")
    Long count(@RequestBody RoleQuery roleQuery);

    @PostMapping(value = "/user-ws/role/fetch")
    List<Role> fetch(@RequestBody RoleQuery roleQuery);

    @PostMapping(value = "/user-ws/role/delete")
    void delete(@RequestBody Long id);

    @PostMapping(value = "/user-ws/role/deleteAll")
    void deleteAll(@RequestBody List<Long> idList);

    @PostMapping(value = "/user-ws/role/activate")
    boolean activate(@RequestBody Long id);

    @PostMapping(value = "/user-ws/role/activateAll")
    boolean activateAll(@RequestBody List<Long> idList);

    @PostMapping(value = "/user-ws/role/deactivate")
    boolean deactivate(@RequestBody Long id);

    @PostMapping(value = "/user-ws/role/deactivateAll")
    boolean deactivateAll(@RequestBody List<Long> idList);
}
