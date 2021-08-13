package in.home.user.service.entity;

import in.home.user.api.model.Role;
import in.home.user.api.model.User;
import in.home.user.service.framework.BaseEntity;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;

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

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(
      name = "us_users_roles",
      joinColumns = @JoinColumn(name = "us_user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "us_role_id", referencedColumnName = "id"))
  private List<RoleEntity> roleList;

  @Override
  public void copyToDTO(User dto) {
    BeanUtils.copyProperties(this, dto, "roleList");
    List<Role> dtoRoleList =
        this.getRoleList().stream()
            .map(
                role -> {
                  Role dtoRole = Role.builder().build();
                  role.copyToDTO(dtoRole);
                  return dtoRole;
                })
            .collect(Collectors.toList());
    dto.setRoleList(dtoRoleList);
  }

  @Override
  public void copyToEntity(User dto) {
    BeanUtils.copyProperties(dto, this, "roleList");
    List<RoleEntity> entityRoleList =
        dto.getRoleList().stream()
            .map(
                dtoRole -> {
                  RoleEntity role = RoleEntity.builder().build();
                  role.copyToEntity(dtoRole);
                  return role;
                })
            .collect(Collectors.toList());
    this.setRoleList(entityRoleList);
  }
}
