package jason.idv.blog.aspect;

import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import jason.idv.blog.exception.InternalServerErrorException;
import jason.idv.blog.exception.UnauthorizedException;
import jason.idv.blog.service.AuthService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class ApiProtectAspect {

    @Autowired
    private AuthService authService;

    @Around(value = "@annotation(jason.idv.blog.annotation.ApiProtect)")
    public Object apiProtect(ProceedingJoinPoint joinPoint)
            throws Throwable {
        try {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

            boolean authPass = false;

            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("token")) {
                    authPass = authService.authToken(request.getHeader("uuid"), cookie.getValue());
                }
            }

            if (!authPass) {
                throw new UnauthorizedException();
            }

            return joinPoint.proceed();

        } catch (UnauthorizedException e) {
            e.printStackTrace();
            throw new UnauthorizedException("Token驗證失敗");
        }
    }
}
