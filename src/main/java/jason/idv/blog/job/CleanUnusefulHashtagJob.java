package jason.idv.blog.job;

import jason.idv.blog.service.HashTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CleanUnusefulHashtagJob {

    @Autowired
    private HashTagService hashTagService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void execute() {
        hashTagService.removeUnusefulHashtag();
    }
}

