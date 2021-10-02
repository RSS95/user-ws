package in.home.user.api.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
  private Long id;
  private String userName;
  private String email;
  private String password;
  private boolean active;
  private LocalDateTime createdOn;
  private Long createdBy;
  private LocalDateTime updatedOn;
  private Long updatedBy;
  private List<Role> roleList;
}
