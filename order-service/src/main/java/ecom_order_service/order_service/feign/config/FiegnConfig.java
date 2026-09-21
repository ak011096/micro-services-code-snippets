package ecom_order_service.order_service.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FiegnConfig {

    @Bean
    public Logger.Level getLog(){
        return Logger.Level.FULL;
    }
}
