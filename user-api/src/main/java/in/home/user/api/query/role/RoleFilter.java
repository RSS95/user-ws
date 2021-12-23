package in.home.user.api.query.role;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoleFilter {
  private List<Long> idList;
  private List<String> roleNameListIn;
  private List<String> roleNameListLike;
  private Boolean active;
}
