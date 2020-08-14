package jason.idv.blog.service;

import java.util.List;

import jason.idv.blog.dao.ArticleDao;
import jason.idv.blog.model.entity.Article;
import jason.idv.blog.model.vo.ArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao.Jpa repo;

    @Autowired
    private ArticleDao.Mybatis mapper;

    public ArticleVo.Profile getById(Long id) {
        return mapper.findProfile(id);
    }

    public Article getEntityById(Long id) {
        return repo.findById(id).orElseThrow(() -> new NullPointerException("找不到文章"));
    }

    public List<ArticleVo> getAll(String hashtagName) {
        return mapper.findByHashTag(hashtagName);
    }

    @Transactional
    public Article create(Article entity, String accountName) {
        entity.create(accountName);
        return repo.saveAndFlush(entity);
    }

    @Transactional
    public void update(Article entity, String accountName) {
        entity.update(accountName);
        repo.saveAndFlush(entity);
    }

}
