package chat.interfaces;

import chat.interfaces.config.ApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@ComponentScan(value="chat")
@SpringBootApplication
public class ApiApplication
{
    public static void main( String[] args )
    {

        SpringApplication.run(ApiApplication.class,args);
    }
}
