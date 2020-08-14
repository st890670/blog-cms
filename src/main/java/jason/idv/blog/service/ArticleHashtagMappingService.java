package jason.idv.blog.service;

import java.util.List;

import jason.idv.blog.dao.ArticleHashtagMappingDao;
import jason.idv.blog.model.entity.ArticleHashtagMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleHashtagMappingService {

    @Autowired
    private ArticleHashtagMappingDao.Jpa repo;

    @Transactional
    public void removeAndCreate(Long articleId, List<Long> hashtagIds, String accountName) {
        repo.deleteByArticleId(articleId);

        hashtagIds.forEach(hashtagId -> {
            ArticleHashtagMapping entity = ArticleHashtagMapping.builder()
                                                                .articleId(articleId)
                                                                .hashtagId(hashtagId)
                                                                .build();
            entity.create(accountName);
            repo.saveAndFlush(entity);
        });
    }
}
