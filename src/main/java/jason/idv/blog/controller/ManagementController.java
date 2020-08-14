package jason.idv.blog.controller;

import jason.idv.blog.annotation.ApiProtect;
import jason.idv.blog.model.vo.ManagementVo;
import jason.idv.blog.model.vo.WelcomeVo;
import jason.idv.blog.service.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ManagementController {

    @Autowired
    private ManagementService service;

    @ApiProtect
    @GetMapping("/management/{managementId}")
    public ManagementVo queryManagement(@PathVariable Long managementId) {
        return service.queryProfile(managementId);
    }

    @GetMapping("/management/count")
    public Integer countManagement() {
        return service.count();
    }

    @PostMapping("/management")
    public void buildFirstManagement(@RequestBody WelcomeVo vo) {
        service.buildManagement(vo);
    }

    @ApiProtect
    @PutMapping("/management/{managementId}")
    public void updateManagement(
            @PathVariable Long managementId,
            @RequestAttribute("account") String account,
            @RequestBody ManagementVo.Save save) {
        service.updateManagement(managementId, save, account);
    }

    @ApiProtect
    @PutMapping("/management/{managementId}/whisper")
    public void updateWhisper(@RequestAttribute("account") String account, @PathVariable Long managementId,
            @RequestBody ManagementVo.Whisper data) {
        service.updateWhisper(managementId, data, account);
    }
}
