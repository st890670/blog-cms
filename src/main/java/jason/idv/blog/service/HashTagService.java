package jason.idv.blog.service;

import java.util.List;

import jason.idv.blog.dao.HashtagDao;
import jason.idv.blog.model.entity.Hashtag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HashTagService {

    @Autowired
    private HashtagDao.Jpa repo;

    @Autowired
    private HashtagDao.Mybatis mapper;

    public Boolean isExist(String name) {
        return repo.countByName(name) > 0;
    }

    public Hashtag getByName(String name) {
        return repo.findByName(name).orElseThrow(() -> new NullPointerException("找不到HashTag"));
    }

    public List<String> getNamesByArticleId(Long articleId) {
        return mapper.findNameByArticleId(articleId);
    }

    @Transactional
    public void removeUnusefulHashtag(){
        List<Long> unusefuleIds = mapper.findUnuseful();
        repo.removeWithIdIn(unusefuleIds);
    }

    @Transactional
    public Hashtag create(String name, String accountName) {
        Hashtag entity = new Hashtag(name);
        entity.create(accountName);
        return repo.saveAndFlush(entity);
    }
}
