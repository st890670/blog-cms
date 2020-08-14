package jason.idv.blog.crypt;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class SHA256Crypt {
    public String hash(String str) {
        return DigestUtils.sha256Hex(str);
    }
}
