package jason.idv.blog.dao;

import java.util.List;
import java.util.Optional;

import jason.idv.blog.model.entity.Hashtag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public class HashtagDao {

    public interface Jpa extends JpaRepository<Hashtag, Long> {
        Integer countByName(String name);

        Optional<Hashtag> findByName(String name);

        @Modifying
        @Query("DELETE FROM Hashtag WHERE id IN ?1")
        void removeWithIdIn(List<Long> ids);
    }

    @Mapper
    public interface Mybatis {
        @SelectProvider(type = Schema.class, method = "findNameByArticleId")
        List<String> findNameByArticleId(Long articleId);

        @SelectProvider(type = Schema.class, method = "findUnuseful")
        List<Long> findUnuseful();
    }

    public static class Schema {
        public String findNameByArticleId(Long articleId) {
            return new SQL() {{
                SELECT("hashtag.name");
                FROM("article_hashtag_mapping mapping");
                JOIN("hashtag ON hashtag.id = mapping.hashtag_id");
                WHERE("mapping.article_id = #{articleId}");
            }}.toString();
        }

        public String findUnuseful() {
            return new SQL() {{
                SELECT_DISTINCT("hashtag.id");
                FROM("hashtag");
                LEFT_OUTER_JOIN("article_hashtag_mapping mapping ON mapping.hashtag_id = hashtag.id");
                WHERE("mapping.id is null");
            }}.toString();
        }
    }
}
