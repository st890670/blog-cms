package jason.idv.blog.dao;

import jason.idv.blog.model.entity.ArticleHashtagMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public class ArticleHashtagMappingDao {

    public interface Jpa extends JpaRepository<ArticleHashtagMapping, Long> {
        void deleteByArticleId(Long articleId);
    }
}
