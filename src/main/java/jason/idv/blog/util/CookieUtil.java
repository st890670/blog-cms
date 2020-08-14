package jason.idv.blog.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

  public void addCookie(String key, String value, HttpServletResponse resp) {
    Cookie cookie = new Cookie(key, value);
    cookie.setHttpOnly(true);
    resp.addCookie(cookie);
  }

  public void removeCookie(String key, HttpServletResponse resp) {
    Cookie cookie = new Cookie(key, null);
    cookie.setMaxAge(0);
    resp.addCookie(cookie);
  }

}
