package ru.pivovarov.t1.ConsumerService.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.pivovarov.t1.ConsumerService.exception.RestTemplateResponseErrorHandler;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

//    @Bean
//    public RestTemplateBuilder restTemplateBuilder() {
//        return new RestTemplateBuilder().errorHandler(new RestTemplateResponseErrorHandler());
//    }
}
