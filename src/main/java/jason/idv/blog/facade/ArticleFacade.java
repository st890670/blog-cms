package jason.idv.blog.facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jason.idv.blog.crypt.Base64Crypt;
import jason.idv.blog.model.entity.Article;
import jason.idv.blog.model.entity.Attach;
import jason.idv.blog.model.vo.ArticleVo;
import jason.idv.blog.service.ArticleHashtagMappingService;
import jason.idv.blog.service.ArticleService;
import jason.idv.blog.service.AttachService;
import jason.idv.blog.service.HashTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ArticleFacade {

    @Autowired
    private ArticleService service;

    @Autowired
    private HashTagService hashTagService;

    @Autowired
    private ArticleHashtagMappingService articleHashtagMappingService;

    @Autowired
    private AttachService attachService;

    @Autowired
    private Base64Crypt base64Crypt;

    public List<ArticleVo> getAll(ArticleVo.Arg args) {
        List<ArticleVo> articleVos = service.getAll(args.getHashtag());

        if (articleVos.size() > 0) {
            articleVos.forEach(vo -> {
                List<String> hashtags = hashTagService.getNamesByArticleId(vo.getId());
                vo.setHashtags(composeTagString(hashtags));
            });
            return articleVos;
        } else {
            return new ArrayList<>();
        }
    }

    public ArticleVo.Profile getById(Long articleId) throws IOException {
        ArticleVo.Profile result = service.getById(articleId);
        result.setHashtags(hashTagService.getNamesByArticleId(articleId));

        Attach image = attachService.queryOne(Attach.TYPE.ARTICLE, articleId);
        result.setImageName(image.getName());
        Optional.ofNullable(attachService.queryFile(image.getUuid()))
                .ifPresent(imageBytes -> result.setImageContent(base64Crypt.encode(imageBytes)));


        return result;
    }

    private String composeTagString(List<String> hashtags) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int index = 0; index < hashtags.size(); index++) {
            if (index == hashtags.size() - 1) {
                stringBuilder.append(hashtags.get(index));
            } else {
                stringBuilder.append(String.format("%s, ", hashtags.get(index)));
            }
        }
        return stringBuilder.toString();
    }

    @Transactional
    public Long createArticle(ArticleVo.Save save, String accountName) {
        Article article = Article.builder()
                .title(save.getTitle())
                .content(save.getContent())
                .build();

        article = service.create(article, accountName);

        Long articleId = article.getId();

        List<Long> hashtagIds = save.getHashtags()
                .stream()
                .map(hashtagName -> hashTagService.isExist(hashtagName) ?
                        hashTagService.getByName(hashtagName).getId() :
                        hashTagService.create(hashtagName, accountName).getId())
                .collect(Collectors.toList());

        articleHashtagMappingService.removeAndCreate(articleId, hashtagIds, accountName);

        return articleId;
    }

    @Transactional
    public void updateArticle(Long articleId, ArticleVo.Save save, String accountName) {
        Article article = service.getEntityById(articleId);
        article.setTitle(save.getTitle());
        article.setContent(save.getContent());
        service.update(article, accountName);

        List<Long> hashtagIds = save.getHashtags()
                .stream()
                .map(hashtagName -> hashTagService.isExist(hashtagName) ?
                        hashTagService.getByName(hashtagName).getId() :
                        hashTagService.create(hashtagName, accountName).getId())
                .collect(Collectors.toList());

        articleHashtagMappingService.removeAndCreate(articleId, hashtagIds, accountName);
    }

    @Transactional
    public void saveAttach(Long articleId, List<MultipartFile> multipartFiles, String accountName) {
        attachService.removeByTypeAndSrcId(Attach.TYPE.ARTICLE, articleId);

        multipartFiles.forEach(
                multipartFile -> attachService.create(multipartFile, Attach.TYPE.ARTICLE, articleId, accountName));
    }

}
