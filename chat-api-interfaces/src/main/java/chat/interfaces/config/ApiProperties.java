package chat.interfaces.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "chatbot-api")
public class ApiProperties {

    private String groupId;

    private String cookie;

}
