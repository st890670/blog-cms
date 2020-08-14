package jason.idv.blog.dao;

import java.util.List;

import jason.idv.blog.model.entity.Article;
import jason.idv.blog.model.vo.ArticleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.repository.JpaRepository;

public class ArticleDao {

    public interface Jpa extends JpaRepository<Article, Long> {

    }

    @Mapper
    public interface Mybatis {

        @SelectProvider(type = Schema.class, method = "findProfile")
        ArticleVo.Profile findProfile(Long id);

        @SelectProvider(type = Schema.class, method = "findByHashTag")
        List<ArticleVo> findByHashTag(String name);
    }

    public static class Schema {

        public String findByHashTag(String name) {
            return new SQL() {{
                SELECT_DISTINCT("article.id," +
                                "article.title," +
                                "article.modified_date AS modifiedDate");
                FROM("hashtag tag");
                JOIN("article_hashtag_mapping mapping ON mapping.hashtag_id = tag.id");
                JOIN("article ON mapping.article_id = article.id");
                if (Strings.isNotEmpty(name)) {
                    WHERE("tag.name LIKE '%' || #{name} || '%'");
                }
            }}.toString();
        }

        public String findProfile(Long id) {
            return new SQL() {{
                SELECT("title," +
                       "content," +
                       "modified_user AS modifiedUser," +
                       "modified_date AS modifiedDate");
                FROM("article");
                WHERE("id = #{id}");
            }}.toString();
        }

    }

}
