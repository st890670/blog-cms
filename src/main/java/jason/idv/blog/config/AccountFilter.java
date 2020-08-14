package jason.idv.blog.config;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class AccountFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String encodedAccount = req.getHeader("account");

        if (encodedAccount != null) {
            request.setAttribute("account", URLDecoder.decode(encodedAccount,
                                                              StandardCharsets.UTF_8));
        }

        chain.doFilter(request, response);
    }
}
