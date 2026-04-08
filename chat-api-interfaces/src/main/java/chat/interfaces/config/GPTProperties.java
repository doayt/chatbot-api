package chat.interfaces.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "gpt")
public class GPTProperties {
    String uri;

    String apiKey;

    String model;

}
