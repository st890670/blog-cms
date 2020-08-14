package jason.idv.blog.dao;

import jason.idv.blog.model.entity.Management;
import jason.idv.blog.model.vo.ManagementVo;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.data.jpa.repository.JpaRepository;

public class ManagementDao {

  public interface Jpa extends JpaRepository<Management, Long> {
    Optional<Management> findByAccountAndWhisper(String account, String whisper);
  }

  @Mapper
  public interface Mybatis {
    @SelectProvider(type = Schema.class, method = "findProfile")
    ManagementVo findProfile(Long managementId);
  }

  public static class Schema {
    public String findProfile(Long managementId) {
      return new SQL() {
        {
          SELECT("account," + "name," + "modified_date AS modifiedDate");
          FROM("management");
          WHERE("id = #{managementId}");
        }
      }.toString();
    }
  }
}
