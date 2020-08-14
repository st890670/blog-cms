package jason.idv.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "jason.idv.blog")
@EnableJpaRepositories(basePackages = "jason.idv.blog.dao", considerNestedRepositories = true)
@EnableScheduling
public class BlogApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(BlogApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(BlogApplication.class);
  }
}
