import java.util.Base64;
import org.junit.jupiter.api.Test;

public class JasonTest {

  @Test
  public void test() {
    System.out.println(new String(Base64.getDecoder()
        .decode("eyJpZCI6NCwiYWNjb3VudCI6Imphc29uIiwibmFtZSI6Iuafj+mZniJ9".getBytes())));
  }
}
