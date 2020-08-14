package jason.idv.blog.model.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "attach")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attach extends BaseEntity.Half {

    @Column
    private String name;

    @Column
    private UUID uuid;

    @Column(name = "content_type")
    private String contentType;

    @Column
    private Byte type;

    @Column(name = "src_id")
    private Long srcId;

    public interface TYPE {
        Byte ARTICLE = 1;
    }
}
