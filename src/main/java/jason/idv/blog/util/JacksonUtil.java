package jason.idv.blog.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JacksonUtil {

  private final static ObjectMapper objectMapper = new ObjectMapper();

  public String serializeToString(Object obj) throws JsonProcessingException {
    return objectMapper.writeValueAsString(obj);
  }

  public byte[] serializeToByte(Object obj) throws JsonProcessingException {
    return objectMapper.writeValueAsBytes(obj);
  }

  public <T> T deserialize(String json, Class<T> targetClass) throws JsonProcessingException {
    return objectMapper.readValue(json, targetClass);
  }

  public <T> T deserialize(byte[] content, Class<T> targetClass) throws IOException {
    return objectMapper.readValue(content, targetClass);
  }
}
