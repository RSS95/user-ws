package in.home.user.api.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Role {
  private Long id;
  private String roleName;
  private boolean active;
  private LocalDateTime createdOn;
  private Long createdBy;
  private LocalDateTime updatedOn;
  private Long updatedBy;
}
