package in.home.user.api.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pagination {
  private Long offset;
  private Long limit;
}
