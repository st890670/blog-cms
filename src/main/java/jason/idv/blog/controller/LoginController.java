package jason.idv.blog.controller;

import jason.idv.blog.annotation.ApiProtect;
import jason.idv.blog.exception.ForbiddenException;
import jason.idv.blog.model.vo.LoginVo;
import jason.idv.blog.service.AuthService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public LoginVo login(HttpServletResponse resp, @RequestBody LoginVo.Arg args)
            throws ForbiddenException {
        return service.login(resp, args);
    }

    @ApiProtect
    @PostMapping("/logout")
    public void logout(HttpServletResponse resp) {
        service.logout(resp);
    }
}
