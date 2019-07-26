package lana.sockserver;


import lana.sockserver.util.objectmapper.ObjectMapper;
import lana.sockserver.util.objectmapper.ObjectMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UtilitiesConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return ObjectMapperImpl.getInstance();
    }
}
