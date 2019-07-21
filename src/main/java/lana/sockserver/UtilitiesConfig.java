package lana.sockserver;

import lana.sockserver.util.hashing.HashingUtil;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilitiesConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public HashingUtil hashingUtil() {
        return null;
    }
}
