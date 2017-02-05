package async;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

/**
 * Created by shengfalin on 2/4/17.
 */
@Configuration
public class BeanConfig {

    @Bean
    public AsyncRestTemplate getAsyncRestTemplate() {
        return new AsyncRestTemplate();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplateBuilder().build();
    }
}
