package jason.idv.blog.service;

import java.util.Optional;
import java.util.UUID;

import jason.idv.blog.dao.ManagementDao;
import jason.idv.blog.model.entity.Management;
import jason.idv.blog.model.vo.ManagementVo;
import jason.idv.blog.model.vo.WelcomeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManagementService {

    @Autowired
    private ManagementDao.Jpa repo;

    @Autowired
    private ManagementDao.Mybatis mapper;

    @Autowired
    private AuthService authService;

    public Integer count() {
        return repo.findAll().size();
    }

    public Optional<Management> getManagement(String account, String whisper) {
        return repo.findByAccountAndWhisper(account, whisper);
    }

    public ManagementVo queryProfile(Long managementId) {
        return mapper.findProfile(managementId);
    }

    @Transactional
    public void buildManagement(WelcomeVo vo) {
        Management management =
                Management.builder()
                          .account(vo.getAccount())
                          .whisper(authService.hashAndEncodeWhisper(vo.getWhisper()))
                          .name(vo.getName())
                          .uuid(UUID.randomUUID())
                          .build();

        management.create("system");
        repo.save(management);
    }

    @Transactional
    public void updateManagement(Long managementId, ManagementVo.Save save, String accountName) {
        Management management =
                repo.findById(managementId).orElseThrow(() -> new NullPointerException("找不到管理員"));
        management.setName(save.getName());
        management.update(accountName);
        repo.saveAndFlush(management);
    }

    @Transactional
    public void updateWhisper(Long managementId, ManagementVo.Whisper update, String accountName) {
        Management management =
                repo.findById(managementId).orElseThrow(() -> new NullPointerException("找不到管理員"));
        management.setWhisper(authService.hashAndEncodeWhisper(update.getWhisper()));
        management.update(accountName);
        repo.saveAndFlush(management);
    }
}
