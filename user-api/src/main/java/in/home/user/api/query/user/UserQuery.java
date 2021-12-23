package in.home.user.api.query.user;

import in.home.user.api.query.Pagination;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserQuery {
  private Pagination pagination;
  private UserFilter userFilter;
}
