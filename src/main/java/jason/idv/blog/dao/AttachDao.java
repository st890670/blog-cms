package jason.idv.blog.dao;

import java.util.List;

import jason.idv.blog.model.entity.Attach;
import org.springframework.data.jpa.repository.JpaRepository;

public class AttachDao {

    public interface Jpa extends JpaRepository<Attach, Long> {
        void deleteByTypeAndSrcId(Byte type, Long srcId);
        List<Attach> findByTypeAndSrcId(Byte type, Long srcId);
    }
}
