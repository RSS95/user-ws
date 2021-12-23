package in.home.user.api.query.role;

import in.home.user.api.query.Pagination;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleQuery {
  private Pagination pagination;
  private RoleFilter roleFilter;
}
