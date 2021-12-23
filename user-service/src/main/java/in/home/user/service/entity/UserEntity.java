package in.home.user.service.entity;

import in.home.user.api.model.Role;
import in.home.user.api.model.User;
import in.home.user.service.framework.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "us_user")
public class UserEntity extends BaseEntity<User> {

  @Column(name = "user_name", nullable = false, unique = true)
  private String userName;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "us_users_roles",
      joinColumns = @JoinColumn(name = "us_user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "us_role_id", referencedColumnName = "id"))
  private Set<RoleEntity> roleEntitySet;

  @Override
  public void copyToDTO(User dto) {
    BeanUtils.copyProperties(this, dto, "password", "roleEntitySet");
    Set<Role> dtoRoleSet =
        this.getRoleEntitySet().stream()
            .map(
                role -> {
                  Role dtoRole = Role.builder().build();
                  role.copyToDTO(dtoRole);
                  return dtoRole;
                })
            .collect(Collectors.toSet());
    dto.setRoleSet(dtoRoleSet);
  }

  @Override
  public void copyToEntity(User dto) {
    BeanUtils.copyProperties(dto, this, "password", "roleEntitySet");
    Set<RoleEntity> entityRoleSet =
        dto.getRoleSet().stream()
            .map(
                dtoRole -> {
                  RoleEntity role = RoleEntity.builder().build();
                  role.copyToEntity(dtoRole);
                  return role;
                })
            .collect(Collectors.toSet());
    this.setRoleEntitySet(entityRoleSet);
  }
}
