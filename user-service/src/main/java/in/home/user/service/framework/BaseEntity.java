package in.home.user.service.framework;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity<D> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "active", nullable = false)
  private boolean active;

  @Column(name = "created_on")
  @CreationTimestamp
  private LocalDateTime createdOn;

  @Column(name = "created_by")
  private Long createdBy;

  @Column(name = "updated_on")
  @UpdateTimestamp
  private LocalDateTime updatedOn;

  @Column(name = "updated_by")
  private Long updatedBy;

  public void copyToDTO(D dto) {
    BeanUtils.copyProperties(this, dto);
  }

  public void copyToEntity(D dto) {
    BeanUtils.copyProperties(dto, this);
  }
}
