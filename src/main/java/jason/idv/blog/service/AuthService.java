package jason.idv.blog.service;

import javax.servlet.http.HttpServletResponse;

import jason.idv.blog.crypt.Base64Crypt;
import jason.idv.blog.crypt.SHA256Crypt;
import jason.idv.blog.exception.ForbiddenException;
import jason.idv.blog.model.entity.Management;
import jason.idv.blog.model.vo.LoginVo;
import jason.idv.blog.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static jason.idv.blog.constant.AuthConstant.TOKEN_SALT;

@Service
public class AuthService {

    @Autowired
    private Base64Crypt base64Crypt;

    @Autowired
    private SHA256Crypt sha256Crypt;

    @Autowired
    private CookieUtil cookieUtil;

    @Autowired
    private ManagementService managementService;

    public LoginVo login(HttpServletResponse resp, LoginVo.Arg args) throws ForbiddenException {
        try {
            String convertedWhisper = hashAndEncodeWhisper(args.getWhisper());

            Management management =
                    managementService.getManagement(args.getAccount(), convertedWhisper)
                                     .orElseThrow(() -> new ForbiddenException("帳號或密碼錯誤"));


            cookieUtil.addCookie("token", generateToken(management.getUuid().toString()), resp);

            return LoginVo.builder()
                          .id(management.getId())
                          .account(management.getAccount())
                          .uuid(management.getUuid())
                          .name(management.getName())
                          .build();

        } catch (ForbiddenException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    public void logout(HttpServletResponse resp) {
        cookieUtil.removeCookie("token", resp);
    }

    String hashAndEncodeWhisper(String whisper) {
        String hashStr = sha256Crypt.hash(whisper);
        return base64Crypt.encode(hashStr);
    }

    private String generateToken(String uuidString) {
        return sha256Crypt.hash(uuidString.substring(0, 8) + TOKEN_SALT);
    }

    public Boolean authToken(String key, String token) {
        return generateToken(key).equals(token);
    }
}
