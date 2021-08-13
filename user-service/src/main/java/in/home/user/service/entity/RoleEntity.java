package in.home.user.service.entity;

import in.home.user.api.model.Role;
import in.home.user.service.framework.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "us_role")
public class RoleEntity extends BaseEntity<Role> {

  @Column(name = "role_name", unique = true, nullable = false)
  private String roleName;
}
