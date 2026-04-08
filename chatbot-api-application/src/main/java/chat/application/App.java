package chat.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@ComponentScan(value = "chat")
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {

        SpringApplication.run(App.class, args);
    }
}
