package chat.interfaces.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ApiConfiguration {

   // @Bean
    public ApiProperties apiProperties() {
        return new ApiProperties();
    }
}
