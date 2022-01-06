package in.home.user.service.impl;

import in.home.user.api.model.Role;
import in.home.user.api.query.role.RoleQuery;
import in.home.user.api.service.RoleService;
import in.home.user.service.entity.RoleEntity;
import in.home.user.service.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.copyToEntity(role);
        roleRepository.save(roleEntity);
        roleEntity.copyToDTO(role);
        return role;
    }

    @Override
    public List<Role> saveAll(List<Role> roleList) {
        List<RoleEntity> roleEntityList = new ArrayList<>();
        for (Role role : roleList) {
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.copyToEntity(role);
            roleEntityList.add(roleEntity);
        }
        roleRepository.saveAll(roleEntityList);
        roleList.clear();
        for (RoleEntity roleEntity : roleEntityList) {
            Role role = new Role();
            roleEntity.copyToDTO(role);
            roleList.add(role);
        }
        return roleList;
    }

    @Override
    public Role loadUserByRoleName(String roleName) {
        RoleEntity roleEntity = roleRepository.findByRoleName(roleName);
        Role role = new Role();
        roleEntity.copyToDTO(role);
        return role;
    }

    @Override
    public List<Role> findAll() {
        List<RoleEntity> roleEntityList = roleRepository.findAll();
        List<Role> roleList = new ArrayList<>();
        for (RoleEntity roleEntity : roleEntityList) {
            Role role = new Role();
            roleEntity.copyToDTO(role);
            roleList.add(role);
        }
        return roleList;
    }

    @Override
    public Role findById(Long roleId) {
        Optional<RoleEntity> roleEntityOptional = roleRepository.findById(roleId);
        if (roleEntityOptional.isPresent()) {
            Role role = new Role();
            roleEntityOptional.get().copyToDTO(role);
            return role;
        }
        return null;
    }

    @Override
    public Long count(RoleQuery roleQuery) {
        return roleRepository.count(roleQuery);
    }

    @Override
    public List<Role> fetch(RoleQuery roleQuery) {
        List<RoleEntity> roleEntityList = roleRepository.fetch(roleQuery);
        List<Role> roleList = new ArrayList<>();
        for (RoleEntity roleEntity : roleEntityList) {
            Role role = new Role();
            roleEntity.copyToDTO(role);
            roleList.add(role);
        }
        return roleList;
    }

    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
        log.info("Role deleted :: id : {}", id);
    }

    @Override
    public void deleteAll(List<Long> idList) {
        roleRepository.deleteAllById(idList);
        log.info("Roles deleted :: id list : {}", idList);
    }

    @Override
    public boolean activate(Long id) {
        Optional<RoleEntity> roleEntityOp = roleRepository.findById(id);
        if (roleEntityOp.isPresent()) {
            if (!roleEntityOp.get().isActive()) {
                roleEntityOp.get().setActive(true);
                roleRepository.save(roleEntityOp.get());
                log.info("Role activated :: id : {}", id);
                return true;
            }
            else {
                log.warn("Role already active :: id : {}", id);
                return false;
            }
        }
        log.error("Role not found :: id : {}", id);
        return false;
    }

    @Override
    public boolean activateAll(List<Long> idList) {
        List<RoleEntity> roleEntityList = roleRepository.findAllById(idList);
        if (!CollectionUtils.isEmpty(roleEntityList)) {
            roleEntityList.forEach(role -> role.setActive(true));
            roleRepository.saveAll(roleEntityList);
            log.info("Roles activated :: id : {}", idList);
            return true;
        }
        log.error("Roles not found :: id : {}", idList);
        return false;
    }

    @Override
    public boolean deactivate(Long id) {
        Optional<RoleEntity> roleEntityOp = roleRepository.findById(id);
        if (roleEntityOp.isPresent()) {
            if (roleEntityOp.get().isActive()) {
                roleEntityOp.get().setActive(false);
                roleRepository.save(roleEntityOp.get());
                log.info("Role deactivated :: id : {}", id);
                return true;
            }
            else {
                log.warn("Role already deactivated :: id : {}", id);
                return false;
            }
        }
        log.error("Role not found :: id : {}", id);
        return false;
    }

    @Override
    public boolean deactivateAll(List<Long> idList) {
        List<RoleEntity> roleEntityList = roleRepository.findAllById(idList);
        if (!CollectionUtils.isEmpty(roleEntityList)) {
            roleEntityList.forEach(role -> role.setActive(false));
            roleRepository.saveAll(roleEntityList);
            log.info("Roles deactivated :: id : {}", idList);
            return true;
        }
        log.error("Roles not found :: id : {}", idList);
        return false;
    }
}
