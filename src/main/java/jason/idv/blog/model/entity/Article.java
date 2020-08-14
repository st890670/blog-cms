package jason.idv.blog.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "article")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article extends BaseEntity.Full {

  @Column
  private String title;

  @Column
  private String content;

}
