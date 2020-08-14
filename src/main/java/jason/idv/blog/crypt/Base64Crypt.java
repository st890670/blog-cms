package jason.idv.blog.crypt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class Base64Crypt {

    public String encode(String content) {
        return Base64.getEncoder()
                     .encodeToString(content.getBytes(StandardCharsets.UTF_8));
    }

    public String encode(byte[] content) {
        return Base64.getEncoder()
                     .encodeToString(content);
    }

    public String decodeToString(String encodedText) {
        return new String(Base64.getDecoder().decode(encodedText), StandardCharsets.UTF_8);
    }
}
