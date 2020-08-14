package jason.idv.blog.model.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "management")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Management extends BaseEntity.Full {

  @Column
  private UUID uuid;

  @Column
  private String name;

  @Column
  private String account;

  @Column
  private String whisper;
}


