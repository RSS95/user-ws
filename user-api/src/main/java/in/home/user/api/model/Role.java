package in.home.user.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
  private Long id;
  private String roleName;
  private boolean active;
  private LocalDateTime createdOn;
  private Long createdBy;
  private LocalDateTime updatedOn;
  private Long updatedBy;
}
