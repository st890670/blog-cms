package jason.idv.blog.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "article_hashtag_mapping")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleHashtagMapping extends BaseEntity.Half {

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "hashtag_id")
    private Long hashtagId;
}
