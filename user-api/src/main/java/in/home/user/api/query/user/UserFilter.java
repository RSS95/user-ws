package in.home.user.api.query.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserFilter {
  private List<Long> idList;
  private List<String> userNameListIn;
  private List<String> userNameListLike;
  private List<String> emailList;
  private Boolean active;
}
