package jason.idv.blog.model.vo;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
public class LoginVo {
    private Long id;
    private String account;
    private String name;
    private UUID uuid;

    @Getter
    @Setter
    public static class Arg {
        private String account;
        private String whisper;
    }
}
