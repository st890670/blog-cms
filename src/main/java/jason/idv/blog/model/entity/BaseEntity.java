package jason.idv.blog.model.entity;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @MappedSuperclass
  @Data
  public static class Half extends BaseEntity {

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "created_date")
    private Timestamp createdDate;

    public void create(String account) {
      this.createdUser = account;
      this.createdDate = Timestamp.valueOf(LocalDateTime.now());
    }
  }

  @MappedSuperclass
  @Data
  public static class Full extends BaseEntity.Half {

    @Column(name = "modified_user")
    private String modifiedUser;

    @Column(name = "modified_date")
    private Timestamp modifiedDate;

    @Override
    public void create(String account) {
      super.create(account);
      this.modifiedUser = account;
      this.modifiedDate = Timestamp.valueOf(LocalDateTime.now());
    }

    public void update(String account) {
      this.modifiedUser = account;
      this.modifiedDate = Timestamp.valueOf(LocalDateTime.now());
    }

  }

}
