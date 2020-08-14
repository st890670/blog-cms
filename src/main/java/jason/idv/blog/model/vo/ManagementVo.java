package jason.idv.blog.model.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagementVo {
    private String account;
    private String name;
    private Long modifiedDate;

    @Getter
    @Setter
    public static class Save {
        private String name;
    }

    @Getter
    @Setter
    public static class Whisper {
        private String whisper;
    }
}
