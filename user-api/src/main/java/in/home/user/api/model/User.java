package in.home.user.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
