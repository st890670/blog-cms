package jason.idv.blog.model.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleVo {
    private Long id;
    private String title;
    private String hashtags;
    private Long modifiedDate;


    @Getter
    @Setter
    public static class Arg {
        private String title;
        private String hashtag;
    }

    @Getter
    @Setter
    public static class Save {
        private String title;
        private String content;
        private List<String> hashtags;
    }

    @Getter
    @Setter
    public static class Profile {
        private String title;
        private String content;
        private List<String> hashtags;
        private String imageName;
        private String imageContent;
        private String modifiedUser;
        private Long modifiedDate;
    }

}
