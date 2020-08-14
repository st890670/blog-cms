package jason.idv.blog.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import jason.idv.blog.dao.AttachDao;
import jason.idv.blog.model.entity.Attach;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class AttachService {

    @Autowired
    private AttachDao.Jpa repo;

    private Path getAttachDirPath() {
        String rootDir = System.getProperty("user.home");
        return Paths.get(rootDir).resolve("blog-attach");
    }

    public Attach queryOne(Byte type, Long srcId) {
        List<Attach> attaches = repo.findByTypeAndSrcId(type, srcId);
        return attaches.size() > 0 ? attaches.get(0) : null;
    }

    public byte[] queryFile(UUID uuid) throws IOException {
        Path attachPath = getAttachDirPath().resolve(uuid.toString());
        if (Files.exists(attachPath)) {
            return Files.readAllBytes(attachPath);
        } else {
            log.warn("找不到附件實體檔案，附件UUID: {}", uuid);
            return null;
        }
    }

    @Transactional
    public void create(MultipartFile file, Byte type, Long srcId, String accountName) {
        Attach attach = Attach.builder()
                .name(file.getOriginalFilename())
                .uuid(UUID.randomUUID())
                .contentType(file.getContentType())
                .type(type)
                .srcId(srcId)
                .build();
        attach.create(accountName);
        attach = repo.saveAndFlush(attach);

        try {
            landAttach(attach.getUuid(), file.getBytes());
        } catch (IOException e) {
            log.error("附件落地失敗");
            e.printStackTrace();
        }
    }

    private void landAttach(UUID uuid, byte[] content) throws IOException {
        Path attachDirPath = getAttachDirPath();

        if (!Files.isDirectory(attachDirPath)) {
            Files.createDirectory(attachDirPath);
        }

        Files.write(attachDirPath.resolve(uuid.toString()), content);
    }

    @Transactional
    public void removeByTypeAndSrcId(Byte type, Long srcId) {
        repo.deleteByTypeAndSrcId(type, srcId);
    }
}
