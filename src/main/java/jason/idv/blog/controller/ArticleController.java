package jason.idv.blog.controller;

import jason.idv.blog.annotation.ApiProtect;
import jason.idv.blog.facade.ArticleFacade;
import jason.idv.blog.model.vo.ArticleVo;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartRequest;

@RestController
@RequestMapping("/api")
public class ArticleController {

    @Autowired
    private ArticleFacade facade;

    @ApiProtect
    @GetMapping("/articles")
    public List<ArticleVo> queryAll(@ModelAttribute ArticleVo.Arg args) {
        return facade.getAll(args);
    }

    @ApiProtect
    @GetMapping("/article/{articleId}")
    public ArticleVo.Profile queryById(@PathVariable Long articleId) throws IOException {
        return facade.getById(articleId);
    }

    @ApiProtect
    @PostMapping("/article")
    public Long createArticle(@RequestAttribute("account") String account, @RequestBody ArticleVo.Save save) {
        return facade.createArticle(save, account);
    }

    @ApiProtect
    @PutMapping("/article/{articleId}")
    public void updateArticle(@PathVariable Long articleId, @RequestAttribute("account") String account,
            @RequestBody ArticleVo.Save save) {
        facade.updateArticle(articleId, save, account);
    }

    @ApiProtect
    @PutMapping("/article/attach/{articleId}")
    public void uploadAttach(@RequestAttribute("account") String account, @PathVariable Long articleId,
            MultipartRequest multipartRequest) {
        facade.saveAttach(articleId, multipartRequest.getFiles("article"), account);
    }
}
