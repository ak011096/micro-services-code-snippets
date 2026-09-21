package ecom_order_service.order_service.feign.config;


import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InventoryFeignClientConfig {

    @Bean
    public Logger.Level getInventoryLogs(){
        return Logger.Level.BASIC;
    }

}
